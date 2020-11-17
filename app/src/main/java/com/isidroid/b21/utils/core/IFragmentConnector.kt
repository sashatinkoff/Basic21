package com.isidroid.b21.utils.core

import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment

interface IFragmentConnector {
    var currentFragment: Fragment?

    @CallSuper
    fun onFragmentResumed(fr: Fragment) {
        this.currentFragment = fr
    }

    @CallSuper
    fun onFragmentPaused(fr: Fragment) {
    }
}