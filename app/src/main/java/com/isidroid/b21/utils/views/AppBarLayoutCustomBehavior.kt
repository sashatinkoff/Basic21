package com.isidroid.b21.utils.views

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout

class AppBarLayoutCustomBehavior(context: Context? = null, attrs: AttributeSet? = null) :
    AppBarLayout.Behavior(context, attrs) {
    private var setIntercept = false
    private var lockAppBar = false
    var dragCallback: DragCallback = object : DragCallback() {
        override fun canDrag(appBarLayout: AppBarLayout): Boolean {
            return !lockAppBar
        }
    }

    init {
        setDragCallback(dragCallback)
    }

    override fun onInterceptTouchEvent(
        parent: CoordinatorLayout,
        child: AppBarLayout,
        ev: MotionEvent
    ): Boolean {
        super.onInterceptTouchEvent(parent, child, ev)
        return setIntercept
    }

    fun lockAppBar() {
        lockAppBar = true
    }

    fun unlockAppBar() {
        lockAppBar = false
    }

    fun attachAppBar(appBarLayout: AppBarLayout) = apply {
        (appBarLayout.layoutParams as CoordinatorLayout.LayoutParams).behavior = this
    }

    @SuppressLint("ClickableViewAccessibility")
    fun attachToolbar(view: View) {
        view.setOnTouchListener { _, event ->
            val result = when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    setIntercept = true
                    true
                }
                MotionEvent.ACTION_CANCEL -> {
                    setIntercept = false
                    true
                }
                else -> false
            }
            result
        }
    }
}