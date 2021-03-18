package com.isidroid.b21.utils.base

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import com.isidroid.b21.utils.core.CoreBindFullscreenDialogFragment

abstract class BindFullscreenDialogFragment<T : ViewDataBinding>(
    @LayoutRes private val layoutRes: Int
) : CoreBindFullscreenDialogFragment<T>(layoutRes)