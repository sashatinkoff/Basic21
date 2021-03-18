package com.isidroid.b21.utils.base

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import com.isidroid.b21.utils.core.CoreBindActivity

abstract class BindActivity<T : ViewDataBinding>(
    @LayoutRes private val layoutRes: Int
) : CoreBindActivity<T>(layoutRes)