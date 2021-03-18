package com.isidroid.b21.utils.base

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import com.isidroid.b21.utils.core.CoreBindBottomSheetDialogFragment

abstract class BindBottomSheetDialogFragment<T : ViewDataBinding>(
    @LayoutRes private val layoutRes: Int
) :  CoreBindBottomSheetDialogFragment<T>(layoutRes)