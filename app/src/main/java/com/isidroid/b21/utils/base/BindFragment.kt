package com.isidroid.b21.utils.base

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import com.isidroid.b21.utils.core.CoreBindFragment

abstract class BindFragment<T : ViewDataBinding>(
    @LayoutRes private val layoutRes: Int
) : CoreBindFragment<T>(layoutRes)