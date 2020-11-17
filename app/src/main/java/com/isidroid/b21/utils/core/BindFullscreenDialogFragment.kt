package com.isidroid.b21.utils.core

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDialogFragment
import com.isidroid.b21.R
import com.isidroid.b21.clean.domain.IBillingUseCase
import com.isidroid.b21.di.ViewModelFactory
import javax.inject.Inject


abstract class BindFullscreenDialogFragment(
    private val layoutRes: Int,
    private val canceledOnTouchOutside: Boolean = true
) : AppCompatDialogFragment(), IBillingUseCase.Listener {
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


    protected open fun onCreateViewModel() {}

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return object : Dialog(requireActivity(), theme) {
            override fun onBackPressed() {
                this@BindFullscreenDialogFragment.onBackPressed()
            }

        }.apply {
            setStyle(STYLE_NO_TITLE, R.style.FullScreenDialogStyle)
            setCanceledOnTouchOutside(canceledOnTouchOutside)
        }
    }

    protected open fun onBackPressed() {
        Handler().postDelayed({ dismissAllowingStateLoss() }, 100)
    }

    override fun onResume() {
        dialog?.window?.apply {
            attributes.width = WindowManager.LayoutParams.MATCH_PARENT
            attributes.height = WindowManager.LayoutParams.MATCH_PARENT
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        super.onResume()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(layoutRes, container, false)
        .apply {
            if (::billing.isInitialized) billing.addListener(this@BindFullscreenDialogFragment)
            setOnClickListener { dismissAllowingStateLoss() }
        }

    // IBillingUseCase.Listener
    override fun onPurchase(isPremium: Boolean) {}
}