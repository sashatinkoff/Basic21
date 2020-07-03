package com.isidroid.b21.ext

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> AppCompatActivity.observe(data: LiveData<T>, callback: (T) -> Unit) {
    data.observe(this, Observer { callback(it) })
}