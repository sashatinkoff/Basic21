package com.isidroid.b21.ext

import android.app.Activity
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import kotlin.math.roundToInt

internal fun Activity.getRootView(): View = findViewById(android.R.id.content)

fun Activity.isKeyboardOpen(): Boolean {
    val visibleBounds = Rect()
    val errorValue =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50f, resources.displayMetrics)

    getRootView().getWindowVisibleDisplayFrame(visibleBounds)
    val heightDiff = getRootView().height - visibleBounds.height()
    val marginOfError = errorValue.roundToInt()
    return heightDiff > marginOfError
}

fun Activity.hideSoftKeyboard() {
    (getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
        .hideSoftInputFromWindow(getRootView().windowToken, 0)
}


