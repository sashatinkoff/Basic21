package com.isidroid.b21.clean.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.util.AttributeSet
import android.util.Base64
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.bumptech.glide.Glide
import com.isidroid.b21.R
import com.stfalcon.imageviewer.StfalconImageViewer
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_fullscreen.view.*
import timber.log.Timber
import java.util.regex.Matcher
import java.util.regex.Pattern

private const val MAX_CLICK_DURATION = 200
private const val IMAGE_TYPE = 5

class CustomWebView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    private val useCustomCss: Boolean = true
) : WebView(context, attrs, defStyleAttr), View.OnClickListener, View.OnTouchListener {
    var onClick: ((String?) -> Unit)? = null

    private var startClickTime: Long = 0
    private var imageViewer: StfalconImageViewer<String>? = null
    private val images = mutableListOf<String>()

    init {
        create()
        visibility = View.INVISIBLE

        setOnClickListener(this)
        setOnTouchListener(this)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun create() {
        webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                if (useCustomCss) injectCSS()
                visibility = View.VISIBLE
                super.onPageFinished(view, url)
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                visibility = View.INVISIBLE
            }
        }

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
        var quote: String? = null
        var src: String? = null
        while (m.find()) {
            quote = m.group(1)
            src =
                if (quote == null || quote.trim { it <= ' ' }.isEmpty())
                    m.group(2).split("//s+")[0] else m.group(2)

            src?.also { images.add(it) }
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

    private fun showImage(url: String?) {
        url ?: return

        val list: List<String>
        val position = images.indexOf(url).let {
            list = if(it >= 0) images else listOf(url)
            if(it < 0) 0 else it
        }

        imageViewer = StfalconImageViewer.Builder(context, list) { view, url ->
            Glide.with(view).load(url).into(view)
        }
            .withStartPosition(position)
            .withBackgroundColor(Color.BLACK)
            .withHiddenStatusBar(true)
            .allowZooming(true)
            .allowSwipeToDismiss(true)
            .withOverlayView(
                LayoutInflater.from(context)
                    .inflate(R.layout.dialog_fullscreen, null, false)
                    .apply { buttonClose.setOnClickListener { imageViewer?.close() } }
            ).build()


        imageViewer?.show()
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

}