package com.isidroid.b21.ext

import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
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