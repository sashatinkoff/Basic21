package com.isidroid.b21.clean.presentation

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.github.chrisbanes.photoview.PhotoView

object FullscreenImageView {
    fun open(context: Context, url: String) {
        Dialog(context, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen)
            .apply {
                val view = LinearLayout(context)
                view.setBackgroundColor(Color.BLACK)

                val imageView = PhotoView(context)
                imageView.scaleType = ImageView.ScaleType.FIT_CENTER
                view.gravity = Gravity.CENTER
                view.addView(imageView)

                requestWindowFeature(Window.FEATURE_NO_TITLE)
                setCancelable(true)
                setContentView(view)
                show()

                imageView.postDelayed({ Glide.with(imageView).load(url).into(imageView) }, 500)
            }
    }
}