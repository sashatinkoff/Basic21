package com.isidroid.b21.utils.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.isidroid.b21.clean.domain.IBillingUseCase
import com.isidroid.b21.di.ViewModelFactory
import javax.inject.Inject

abstract class BindBottomSheetDialogFragment(
    @LayoutRes private val layoutRes: Int
) : BottomSheetDialogFragment(), IBaseView, IBillingUseCase.Listener {
    @Inject protected lateinit var viewModelFactory: ViewModelFactory
    @Inject lateinit var billing: IBillingUseCase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreateViewModel()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::billing.isInitialized) billing.removeListener(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(layoutRes, container, false)
        .also { if (::billing.isInitialized) billing.addListener(this) }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        createBaseView()
    }

    protected open fun onCreateViewModel() {}

    // IBillingUseCase.Listener
    override fun onPurchase(isPremium: Boolean) {}
}