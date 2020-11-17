package com.isidroid.b21.ext

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import java.io.InvalidClassException
import java.io.Serializable

fun Fragment.put(key: String, value: Any?) =
    apply { putArgument(fragment = this, key = key, value = value) }

fun DialogFragment.put(key: String, value: Any?) =
    apply { putArgument(fragment = this, key = key, value = value) }


fun DialogFragment.showDialog(
    activity: FragmentActivity? = null,
    fragment: Fragment? = null,
    tag: String = this.javaClass.simpleName
) {
    when {
        activity != null -> (activity.supportFragmentManager.findFragmentByTag(tag) as? DialogFragment)?.dialog?.isShowing == true
        fragment != null -> (fragment.childFragmentManager.findFragmentByTag(tag) as? DialogFragment)?.dialog?.isShowing == true
        else -> null
    }?.let { isShowing ->
        if (!isShowing) {
            (activity?.supportFragmentManager ?: fragment?.childFragmentManager)
                ?.beginTransaction()
                ?.apply { show(this, tag) }

        }
    }
}

private fun putArgument(fragment: Fragment, key: String, value: Any?) = fragment.apply {
    value ?: return@apply
    arguments = arguments ?: Bundle()
    when (value) {
        is String -> arguments?.putString(key, value)
        is Serializable -> arguments?.putSerializable(key, value)
        is Long -> arguments?.putLong(key, value)
        is Double -> arguments?.putDouble(key, value)
        is Boolean -> arguments?.putBoolean(key, value)
        is Float -> arguments?.putFloat(key, value)
    }
}