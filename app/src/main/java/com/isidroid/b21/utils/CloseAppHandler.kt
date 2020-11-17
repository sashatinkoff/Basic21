package com.isidroid.b21.utils

import android.app.Activity
import android.graphics.Color
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.isidroid.b21.R

class CloseAppHandler(private val activity: Activity) {
    private val view = activity.findViewById<View>(android.R.id.content)
    private var snackbar: Snackbar? = null

    fun execute() {
        if (activity.isTaskRoot && (snackbar == null || snackbar?.isShown != true)) {
            snackbar =
                Snackbar.make(view, R.string.label_press_back_again_to_close, Snackbar.LENGTH_LONG)
                    .apply {
                        setTextColor(Color.WHITE)
                        show()
                    }
        } else {
            snackbar = null
            activity.finishAfterTransition()
        }
    }
}