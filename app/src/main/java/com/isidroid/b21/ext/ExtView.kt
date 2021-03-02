package com.isidroid.b21.ext

import android.graphics.Color
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import android.widget.EditText
import android.widget.ViewFlipper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import kotlin.random.Random


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

fun View.height(onlyHeight: Boolean = true): Int {
    val params = layoutParams as ViewGroup.MarginLayoutParams
    val offsets = params.topMargin + params.bottomMargin + paddingTop + paddingBottom
    return height + if (onlyHeight) 0 else offsets
}


fun ViewPager2.reduceDragSensitivity() {
    try {
        val recyclerViewField = ViewPager2::class.java.getDeclaredField("mRecyclerView")
        recyclerViewField.isAccessible = true
        val recyclerView = recyclerViewField.get(this) as RecyclerView

        val touchSlopField = RecyclerView::class.java.getDeclaredField("mTouchSlop")
        touchSlopField.isAccessible = true
        val touchSlop = touchSlopField.get(recyclerView) as Int
        touchSlopField.set(recyclerView, touchSlop * 2)       // "8" was obtained experimentally
    } catch (e: Throwable) {
    }
}

fun EditText.doOnEnter(callback: () -> Unit) = setOnKeyListener { _, _, keyEvent ->
    if (keyEvent.action == KeyEvent.ACTION_DOWN && keyEvent.keyCode == KeyEvent.KEYCODE_ENTER) {
        callback()
        true
    } else false
}

fun BottomNavigationView.uncheckAllItems() {
    menu.setGroupCheckable(0, true, false)
    for (i in 0 until menu.size()) {
        menu.getItem(i).isChecked = false
    }
    menu.setGroupCheckable(0, true, true)
}

inline fun TabLayout.doOnTabSelected(crossinline action: (tab: TabLayout.Tab?) -> Unit) =
    addOnTabSelectedListener(onTabSelected = action)

inline fun TabLayout.addOnTabSelectedListener(
    crossinline onTabSelected: (tab: TabLayout.Tab?) -> Unit = {},
    crossinline onTabUnselected: (tab: TabLayout.Tab?) -> Unit = {},
    crossinline onTabReselected: (tab: TabLayout.Tab?) -> Unit = {}
): TabLayout.OnTabSelectedListener {
    val listener = object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab?) = onTabSelected.invoke(tab)
        override fun onTabUnselected(tab: TabLayout.Tab?) = onTabUnselected.invoke(tab)
        override fun onTabReselected(tab: TabLayout.Tab?) = onTabReselected.invoke(tab)
    }

    addOnTabSelectedListener(listener)
    return listener
}

fun ViewFlipper.addOnChangeListener(onChange: (Int) -> Unit) {
    addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
        private var currentDisplayed = -1

        override fun onLayoutChange(
            v: View?,
            left: Int,
            top: Int,
            right: Int,
            bottom: Int,
            oldLeft: Int,
            oldTop: Int,
            oldRight: Int,
            oldBottom: Int
        ) {
            if (currentDisplayed != displayedChild) {
                onChange(displayedChild)
                currentDisplayed = displayedChild
            }
        }
    })
}