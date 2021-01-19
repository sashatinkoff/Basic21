package com.isidroid.b21.clean.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.util.Base64
import android.view.MotionEvent
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

private const val MAX_CLICK_DURATION = 200
private const val IMAGE_TYPE = 5

class CustomWebView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    private val useCustomCss: Boolean = true
) : WebView(context, attrs, defStyleAttr), View.OnClickListener, View.OnTouchListener {
    var onClick: ((String?) -> Unit)? = null

    private var startClickTime: Long = 0

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

    // View.OnClickListener
    override fun onClick(view: View?) {
        val hr = hitTestResult
        try {
            if (hr.type == IMAGE_TYPE) onClick?.invoke(hr.extra)
        } catch (e: Exception) {
            e.printStackTrace()
        }
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