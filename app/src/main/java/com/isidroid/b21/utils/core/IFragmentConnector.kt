package com.isidroid.b21.utils.core

import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import com.isidroid.b21.R

interface IFragmentConnector {
    var currentFragment: Fragment?

    @CallSuper
    fun onFragmentResumed(fr: Fragment) {
        this.currentFragment = fr
    }

    @CallSuper
    fun onFragmentPaused(fr: Fragment) {
    }

    fun openFragment(
        fragment: Fragment?,
        useAnimations: Boolean = false,
        addToBackStack: Boolean = true,
        animationEnter: Int = R.anim.fragment_open_enter,
        animationExit: Int = R.anim.fragment_close_exit,
        animationPopEnter: Int = R.anim.fragment_open_enter,
        animationPopExit: Int = R.anim.fragment_close_exit,
        fragmentTag: String? = if (fragment?.tag.isNullOrEmpty()) fragment?.javaClass?.simpleName else fragment?.tag,
        containerLayoutId: Int? = null
    )
}