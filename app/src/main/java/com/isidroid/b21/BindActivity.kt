package com.isidroid.b21

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.isidroid.b21.di.ViewModelFactory
import com.isidroid.b21.di.appComponent
import javax.inject.Inject

abstract class BindActivity(@LayoutRes private val layoutRes: Int) : AppCompatActivity() {
    @Inject lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent().inject(this)
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ViewDataBinding>(this, layoutRes)
        onCreateViewModel()
    }

    open fun onCreateViewModel() {}
}