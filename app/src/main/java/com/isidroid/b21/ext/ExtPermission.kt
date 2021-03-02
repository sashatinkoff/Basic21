package com.isidroid.b21.ext

import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment


fun Context.hasPermissions(perms: Array<String>): Boolean {
    for (p in perms)
        if (ContextCompat.checkSelfPermission(this, p) != PERMISSION_GRANTED) return false

    return true
}