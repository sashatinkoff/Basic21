package com.isidroid.b21.utils.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.isidroid.b21.clean.domain.IBillingUseCase
import com.isidroid.b21.di.ViewModelFactory
import javax.inject.Inject

abstract class BindBottomSheetDialogFragment<T : ViewDataBinding>(
    @LayoutRes private val layoutRes: Int
) : BottomSheetDialogFragment(), IBaseView, IBillingUseCase.Listener {
    @Inject protected lateinit var viewModelFactory: ViewModelFactory
    @Inject lateinit var billing: IBillingUseCase
    lateinit var binding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreateViewModel()
    }

    override fun onResume() {
        super.onResume()
        if (::billing.isInitialized) billing.addListener(this)
    }

    override fun onPause() {
        super.onPause()
        if (::billing.isInitialized) billing.removeListener(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        return binding.root
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        createBaseView()
    }

    protected open fun onCreateViewModel() {}

    // IBillingUseCase.Listener
    override fun onPurchase(isPremium: Boolean) {}
}