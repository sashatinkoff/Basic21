package com.isidroid.b21.utils.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleObserver
import com.isidroid.b21.R
import com.isidroid.b21.clean.domain.IBillingUseCase
import com.isidroid.b21.di.ViewModelFactory
import com.isidroid.b21.ext.hideSoftKeyboard
import timber.log.Timber
import javax.inject.Inject

/**
 * this class contains the base implementation, do not modify it.
 * To extend the class with your logic use base/Bind*.kt class
 *
 */
abstract class CoreBindFragment<T : ViewDataBinding>(
    @LayoutRes private val layoutRes: Int
) : Fragment(), LifecycleObserver, IBackable, IFragmentConnector, IBaseView,
    IBillingUseCase.Listener {

    @Inject protected lateinit var viewModelFactory: ViewModelFactory
    @Inject lateinit var billing: IBillingUseCase
    lateinit var binding: T

    override var currentFragment: Fragment? = null

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag("fragment_lifecycle").i("onCreate $javaClass")
        lifecycle.addObserver(this)
        onCreateViewModel()
    }

    override fun onResume() {
        super.onResume()
        Timber.tag("fragment_lifecycle").i("onResume $javaClass")
        fragmentConnector(true)

        if (::billing.isInitialized) billing.addListener(this)
    }

    @CallSuper
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

    override fun onPause() {
        super.onPause()
        requireActivity().hideSoftKeyboard()
        fragmentConnector(false)
        if (::billing.isInitialized) billing.removeListener(this)
    }

    @CallSuper
    override fun onHiddenChanged(hidden: Boolean) {
        fragmentConnector(!hidden)
    }

    private fun fragmentConnector(isResumed: Boolean) {
        val connector =
            parentFragment as? IFragmentConnector ?: activity as? IFragmentConnector ?: return

        if (isResumed && !isHidden) connector.onFragmentResumed(this)
        else connector.onFragmentPaused(this)
    }


    open fun onCreateViewModel() {}

    @CallSuper
    open fun showError(
        message: String?,
        isCritical: Boolean = false,
        buttonTitle: String? = null,
        onButtonClick: (() -> Unit)? = null
    ) {
        (requireActivity() as? CoreBindActivity<out ViewDataBinding>)
            ?.showError(message, isCritical, buttonTitle, onButtonClick)
    }

    @CallSuper
    open fun showError(
        t: Throwable?,
        isCritical: Boolean = false,
        buttonTitle: String? = null,
        onButtonClick: (() -> Unit)? = null
    ) {
        (requireActivity() as? CoreBindActivity<out ViewDataBinding>)
            ?.showError(t, isCritical, buttonTitle, onButtonClick)
    }


    override fun openFragment(
        fragment: Fragment?,
        useAnimations: Boolean,
        addToBackStack: Boolean,
        animationEnter: Int,
        animationExit: Int,
        animationPopEnter: Int,
        animationPopExit: Int,
        fragmentTag: String?,
        containerLayoutId: Int?
    ) {
        (requireActivity() as? IFragmentConnector)
            ?.openFragment(
                fragment = fragment,
                useAnimations = useAnimations,
                addToBackStack = addToBackStack,
                animationEnter = animationEnter,
                animationExit = animationExit,
                animationPopEnter = animationPopEnter,
                animationPopExit = animationPopExit,
                fragmentTag = fragmentTag,
                containerLayoutId = containerLayoutId
            )
    }

    // IBackable
    override fun onBackPressed(): Boolean = false

    // IBillingUseCase.Listener
    override fun onPurchase(isPremium: Boolean) {}
}