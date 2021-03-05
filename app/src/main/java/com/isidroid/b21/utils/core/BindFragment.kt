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

abstract class BindFragment(@LayoutRes private val layoutRes: Int) : Fragment(), LifecycleObserver,
    IBackable, IFragmentConnector, IBaseView, IBillingUseCase.Listener {
    @Inject protected lateinit var viewModelFactory: ViewModelFactory
    @Inject lateinit var billing: IBillingUseCase

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
    }

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        DataBindingUtil.inflate<ViewDataBinding>(inflater, layoutRes, container, false).root
            .also {
                if (::billing.isInitialized) billing.addListener(this)
            }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        createForm()
        createToolbar()
        createAdapter()
    }

    override fun onPause() {
        super.onPause()
        requireActivity().hideSoftKeyboard()
        fragmentConnector(false)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::billing.isInitialized) billing.removeListener(this)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        fragmentConnector(!hidden)
    }

    private fun fragmentConnector(isResumed: Boolean) {
        val connector =
            parentFragment as? IFragmentConnector ?: activity as? IFragmentConnector ?: return

        if(isResumed && !isHidden) connector.onFragmentResumed(this)
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
        (requireActivity() as? BindActivity)
            ?.showError(message, isCritical, buttonTitle, onButtonClick)
    }

    @CallSuper
    open fun showError(
        t: Throwable?,
        isCritical: Boolean = false,
        buttonTitle: String? = null,
        onButtonClick: (() -> Unit)? = null
    ) {
        (requireActivity() as? BindActivity)
            ?.showError(t, isCritical, buttonTitle, onButtonClick)
    }

    fun openFragmentSlide(
        fragment: Fragment,
        fragmentTag: String? = if (fragment.tag.isNullOrEmpty()) fragment.javaClass.simpleName else fragment.tag,
        containerLayoutId: Int? = null
    ) {
        (requireActivity() as? BindActivity)?.openFragmentSlide(
            fragment,
            fragmentTag,
            containerLayoutId = containerLayoutId
        )
    }

    fun openFragment(
        fragment: Fragment,
        useAnimations: Boolean = false,
        addToBackStack: Boolean = true,
        animationEnter: Int = R.anim.fragment_open_enter,
        animationExit: Int = R.anim.fragment_close_exit,
        animationPopEnter: Int = R.anim.fragment_open_enter,
        animationPopExit: Int = R.anim.fragment_close_exit,
        fragmentTag: String? = if (fragment.tag.isNullOrEmpty()) fragment.javaClass.simpleName else fragment.tag,
        containerLayoutId: Int? = null
    ) = (requireActivity() as? BindActivity)
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

    // IBackable
    override fun onBackPressed(): Boolean = false

    // IBillingUseCase.Listener
    override fun onPurchase(isPremium: Boolean) {}
}