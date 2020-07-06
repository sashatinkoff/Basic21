package com.isidroid.b21.utils

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.isidroid.b21.di.ViewModelFactory
import com.isidroid.b21.di.appComponent
import com.isidroid.b21.ext.hideSoftKeyboard
import javax.inject.Inject

abstract class BindActivity(@LayoutRes private val layoutRes: Int) : AppCompatActivity() {
    @Inject lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ViewDataBinding>(this, layoutRes)
        onCreateViewModel()
    }

    override fun onPause() {
        super.onPause()
        hideSoftKeyboard()
    }

    open fun onCreateViewModel() {}
}