package com.isidroid.b21.ext

import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import androidx.core.content.ContextCompat


fun Context.hasPermissions(vararg  perms: String): Boolean {
    for (p in perms)
        if (ContextCompat.checkSelfPermission(this, p) != PERMISSION_GRANTED) return false

    return true
}