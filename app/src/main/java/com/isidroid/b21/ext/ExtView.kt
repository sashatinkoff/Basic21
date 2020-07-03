package com.isidroid.b21.ext

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager

fun View.enable(enabled: Boolean, alpha: Float = .6f) = apply {
    this.alpha = if (enabled) 1f else alpha
    isEnabled = enabled
}

fun View.visible(isVisible: Boolean, isInvisible: Boolean = false) {
    visibility = when {
        isVisible -> View.VISIBLE
        isInvisible -> View.INVISIBLE
        else -> View.GONE
    }
}

fun View.hideSoftKeyboard() {
    val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun View.height(onlyHeight: Boolean = true): Int {
    val params = layoutParams as ViewGroup.MarginLayoutParams
    val offsets = params.topMargin + params.bottomMargin + paddingTop + paddingBottom
    return height + if (onlyHeight) 0 else offsets
}