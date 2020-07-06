package com.isidroid.b21.ext

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LifecycleOwner.observe(data: LiveData<T>, callback: (T) -> Unit) {
    data.observe(this, Observer { callback(it) })
}

fun AppCompatActivity.permission(
    permission: String,
    onGranted: () -> Unit,
    onShowRationale: (() -> Unit)? = null,
    onDenied: (() -> Unit)? = null
) {
    val code = ContextCompat.checkSelfPermission(this, permission)

    when {
        code == PackageManager.PERMISSION_GRANTED -> onGranted()
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && shouldShowRequestPermissionRationale(permission) -> onShowRationale?.invoke()
        else -> registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) onGranted() else onDenied?.invoke()
        }.launch(permission)
    }
}

fun Context?.alert(
    style: Int? = null,
    titleRes: Int? = null, messageRes: Int? = null,
    title: String? = null, message: String? = null,
    positiveRes: Int? = null, positive: String? = null,
    negativeRes: Int? = null, negative: String? = null,
    neutralRes: Int? = null, neutral: String? = null,
    onPositive: () -> Unit = {}, onNeutral: () -> Unit = {}, onNegative: () -> Unit = {},
    view: View? = null,
    items: Array<out CharSequence>? = null,
    onItemSelected: ((Int, String) -> Unit)? = null,
    onDismiss: (() -> Unit)? = null,
    isCancelable: Boolean = true
): AlertDialog? {
    this ?: return null
    val builder = style
        ?.let { AlertDialog.Builder(this, it) }
        ?: run { AlertDialog.Builder(this@alert) }
            .setOnDismissListener { onDismiss?.invoke() }
            .setCancelable(isCancelable)

    if (view != null) builder.setView(view)

    when {
        title != null -> builder.setTitle(title)
        titleRes != null -> builder.setTitle(titleRes)
    }

    when {
        view == null && message != null -> builder.setMessage(message)
        view == null && messageRes != null -> builder.setMessage(messageRes)
    }

    when {
        positive != null -> builder.setPositiveButton(positive) { _, _ -> onPositive() }
        positiveRes != null -> builder.setPositiveButton(positiveRes) { _, _ -> onPositive() }
    }

    when {
        negative != null -> builder.setNegativeButton(negative) { _, _ -> onNegative() }
        negativeRes != null -> builder.setNegativeButton(negativeRes) { _, _ -> onNegative() }
    }

    when {
        neutral != null -> builder.setNeutralButton(neutral) { _, _ -> onNeutral() }
        neutralRes != null -> builder.setNeutralButton(neutralRes) { _, _ -> onNeutral() }
    }

    items?.let {
        builder.setItems(items) { _, pos ->
            onItemSelected?.invoke(
                pos,
                items[pos].toString()
            )
        }
    }
    return builder.create().apply { show() }
}
