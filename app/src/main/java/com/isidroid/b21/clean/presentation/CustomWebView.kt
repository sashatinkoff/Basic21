package com.isidroid.b21.clean.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.util.AttributeSet
import android.util.Base64
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.webkit.*
import androidx.annotation.CallSuper
import androidx.annotation.RequiresApi
import com.isidroid.b21.GlideApp
import com.isidroid.b21.R
import com.stfalcon.imageviewer.StfalconImageViewer
import kotlinx.android.synthetic.main.dialog_fullscreen.view.*
import java.util.regex.Matcher
import java.util.regex.Pattern

private const val MAX_CLICK_DURATION = 200
private const val IMAGE_TYPE = 5

class CustomWebView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    var useCustomCss: Boolean = true,
    var onClick: ((String?) -> Unit)? = null
) : WebView(context, attrs, defStyleAttr), View.OnClickListener, View.OnTouchListener {
    private var startClickTime: Long = 0
    private var imageViewer: StfalconImageViewer<String>? = null
    private val images = mutableListOf<Img>()
    private val srcRegex = "src\\s*=\\s*\"([^\"]+)\"".toRegex()
    private val altRegex = "alt\\s*=\\s*\"([^\"]+)\"".toRegex()

    init {
        create()
        visibility = View.INVISIBLE

        setOnClickListener(this)
        setOnTouchListener(this)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun create() {
        webViewClient = CustomClient(context)

        with(settings) {
            javaScriptEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
        }
    }

    override fun loadData(data: String?, mimeType: String?, encoding: String?) {
        super.loadData(data, mimeType, encoding)
        findImages(data.orEmpty())
    }

    override fun loadDataWithBaseURL(
        baseUrl: String?,
        data: String?,
        mimeType: String?,
        encoding: String?,
        historyUrl: String?
    ) {
        super.loadDataWithBaseURL(baseUrl, data, mimeType, encoding, historyUrl)
        findImages(data.orEmpty())
    }

    private fun injectCSS() {
        try {
            val inputStream = context.resources.assets.open("style.css")
            val buffer = ByteArray(inputStream.available())
            inputStream.read(buffer)
            inputStream.close()
            val encoded = Base64.encodeToString(buffer, Base64.NO_WRAP)

            loadUrl(
                "javascript:(function() {" +
                        "var st = document.getElementsByTagName('style');" +
                        "for(i = 0 ; i < st.length ; i++){" +
                        "st[i].parentNode.removeChild(st[i]);" +
                        "}" +
                        "var parent = document.getElementsByTagName('head').item(0);" +
                        "var style = document.createElement('style');" +
                        "style.type = 'text/css';" +  // Tell the browser to BASE64-decode the string into your script !!!
                        "style.innerHTML = window.atob('" + encoded + "');" +
                        "parent.appendChild(style)" +
                        "})()"
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun findImages(html: String) {
        images.clear()
        val p: Pattern = Pattern.compile(
            "<img\\b[^>]*\\bsrc\\b\\s*=\\s*(['\"])?([^'\"\n\r\\f>]+(\\.jpg|\\.bmp|\\.eps|\\.gif|\\.mif|\\.miff|\\.png|\\.tif|\\.tiff|\\.svg|\\.wmf|\\.jpe|\\.jpeg|\\.dib|\\.ico|\\.tga|\\.cut|\\.pic|\\b)\\b)[^>]*>",
            Pattern.CASE_INSENSITIVE
        )
        val m: Matcher = p.matcher(html)
        while (m.find()) {
            val htmlTag = m.group(0)
            try {
                val src = srcRegex.find(htmlTag)?.groupValues?.lastOrNull()
                val alt = altRegex.find(htmlTag)?.groupValues?.lastOrNull().orEmpty()

                src?.let { images.add(Img(url = src, description = alt)) }
            } catch (e: Throwable) {
            }
        }
    }

    // View.OnClickListener
    override fun onClick(view: View?) {
        val hr = hitTestResult
        try {
            if (hr.type == IMAGE_TYPE) onClick?.invoke(hr.extra) ?: showImage(hr.extra)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showImage(url: String?) {
        url ?: return

        val list: List<String>
        val position = images.indexOfFirst { it.url == url }.let {
            list = if (it >= 0) images.map { it.url } else listOf(url)
            if (it < 0) 0 else it
        }

        val overlayView by lazy {
            LayoutInflater.from(context)
                .inflate(R.layout.dialog_fullscreen, null, false)
                .apply {
                    toolbar.setNavigationOnClickListener { imageViewer?.close() }
                }
        }


        updateLayout(view = overlayView, position = position, limit = list.size)

        imageViewer = StfalconImageViewer.Builder(context, list) { view, url ->
            GlideApp.with(view).load(url).into(view)
        }
            .withImageChangeListener {
                updateLayout(view = overlayView, position = it, limit = list.size)
            }
            .withStartPosition(position)
            .withBackgroundColor(Color.BLACK)
            .withHiddenStatusBar(true)
            .allowZooming(true)
            .allowSwipeToDismiss(true)
            .withOverlayView(overlayView)
            .build()


        imageViewer?.show()
    }

    private fun updateLayout(view: View, position: Int, limit: Int) = view.apply {
        textView.text = images[position].description
        toolbar.title =
            String.format(context.getString(R.string.image_counter), position + 1, limit)
    }

    // View.OnTouchListener
    override fun onTouch(view: View?, event: MotionEvent?): Boolean {
        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {
                startClickTime = System.currentTimeMillis()
            }
            MotionEvent.ACTION_UP -> {
                val clickDuration = System.currentTimeMillis() - startClickTime
                if (clickDuration < MAX_CLICK_DURATION) performClick()
            }
        }
        return false
    }

    open class CustomClient(private val context: Context) : WebViewClient() {

        @CallSuper
        override fun onPageFinished(view: WebView?, url: String?) {
            (view as? CustomWebView)?.also { v ->
                if (v.useCustomCss) v.injectCSS()
                v.visibility = View.VISIBLE
            }


            view?.visibility = View.VISIBLE
            super.onPageFinished(view, url)
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            view?.visibility = View.INVISIBLE
        }

        override fun shouldOverrideUrlLoading(view: WebView?, url: String?) =
            handleUrl(Uri.parse(url.orEmpty()))

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?) =
            handleUrl(request?.url)

        private fun handleUrl(uri: Uri?): Boolean {
            uri ?: return true

            return if (uri.host?.contains("google.com") == true) false
            else Intent(ACTION_VIEW, uri)
                .let {
                    context.startActivity(it)
                    false
                }
        }
    }

    data class Img(val url: String, val description: String = "")
}