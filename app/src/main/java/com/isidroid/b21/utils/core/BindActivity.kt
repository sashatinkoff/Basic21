package com.isidroid.b21.utils.core

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.isidroid.b21.R
import com.isidroid.b21.clean.domain.IBillingUseCase
import com.isidroid.b21.di.ViewModelFactory
import com.isidroid.b21.ext.alert
import com.isidroid.b21.ext.hideSoftKeyboard
import com.isidroid.b21.models.settings.Settings
import com.isidroid.b21.utils.CloseAppHandler
import timber.log.Timber
import javax.inject.Inject

abstract class BindActivity(@LayoutRes private val layoutRes: Int) : AppCompatActivity(),
    IFragmentConnector, IBaseView, IBillingUseCase.Listener {
    @Inject lateinit var viewModelFactory: ViewModelFactory
    @Inject lateinit var billing: IBillingUseCase

    private var errorDialog: AlertDialog? = null
    private val closeAppHandler by lazy { CloseAppHandler(this) }
    override var currentFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(Settings.theme)
        Timber.tag("activity_lifecycle").i("${javaClass.simpleName} onCreate")
        super.onCreate(savedInstanceState)

        if (::billing.isInitialized) billing.addListener(this)
        DataBindingUtil.setContentView<ViewDataBinding>(this, layoutRes)

        createBaseView()
        onCreateViewModel()
    }

    override fun onResume() {
        AppCompatDelegate.setDefaultNightMode(Settings.theme)
        super.onResume()
        Timber.tag("activity_lifecycle").i("${javaClass.simpleName} onResume")
    }

    override fun onDestroy() {
        super.onDestroy()

        if (::billing.isInitialized) billing.removeListener(this)
        Timber.tag("activity_lifecycle").i("${javaClass.simpleName} onDestroy")
    }

    override fun onPause() {
        super.onPause()
        hideSoftKeyboard()
    }

    override fun onBackPressed() {
        closeAppHandler.execute()
    }

    open fun onCreateViewModel() {}

    @CallSuper
    open fun showError(
        t: Throwable?,
        isCritical: Boolean = false,
        buttonTitle: String? = null,
        onButtonClick: (() -> Unit)? = null
    ) {
        showError(
            message = t?.message,
            isCritical = isCritical,
            buttonTitle = buttonTitle,
            onButtonClick = onButtonClick
        )
    }

    @CallSuper
    open fun showError(
        message: String?,
        isCritical: Boolean = false,
        buttonTitle: String? = null,
        onButtonClick: (() -> Unit)? = null
    ) {
        message ?: return

        if (isCritical && errorDialog?.isShowing != true)
            errorDialog = alert(
                message = message,
                positive = buttonTitle,
                onPositive = { onButtonClick?.invoke() },
                onDismiss = { errorDialog = null },
                negativeRes = android.R.string.cancel,
                isCancelable = false
            )
        else if (!isCritical)
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun currentFragment() =
        supportFragmentManager.fragments.lastOrNull {
            it.javaClass.canonicalName.toString().startsWith("com.isidroid.b21") && it.isVisible
        }

    open fun openFragmentSlide(
        fragment: Fragment,
        fragmentTag: String? = if (fragment.tag.isNullOrEmpty()) fragment.javaClass.simpleName else fragment.tag,
        containerLayoutId: Int? = null
    ) = openFragment(
        fragment = fragment,
        useAnimations = true,
        addToBackStack = true,
        animationEnter = R.anim.open_slide_enter,
        animationExit = R.anim.empty,
        animationPopEnter = R.anim.empty,
        animationPopExit = R.anim.pop_exit,
        fragmentTag = fragmentTag,
        containerLayoutId = containerLayoutId
    )

    open fun openFragment(
        fragment: Fragment,
        useAnimations: Boolean = false,
        addToBackStack: Boolean = true,
        animationEnter: Int = R.anim.fragment_open_enter,
        animationExit: Int = R.anim.fragment_close_exit,
        animationPopEnter: Int = R.anim.fragment_open_enter,
        animationPopExit: Int = R.anim.fragment_close_exit,
        fragmentTag: String? = if (fragment.tag.isNullOrEmpty()) fragment.javaClass.simpleName else fragment.tag,
        containerLayoutId: Int? = null
    ) {
        // check whether the currently displayed
        if (currentFragment()?.tag == fragmentTag) return

        supportFragmentManager.beginTransaction().apply {
            if (useAnimations) setCustomAnimations(
                animationEnter, animationExit,
                animationPopEnter, animationPopExit
            )

            currentFragment()?.let { hide(it) }
            supportFragmentManager.findFragmentByTag(fragmentTag)
                ?.let { show(it) }
                ?: run {
                    val id = containerLayoutId ?: R.id.container
                    add(id, fragment, fragmentTag)
                    if (addToBackStack) addToBackStack(fragmentTag)
                }

            commitAllowingStateLoss()
        }
    }

    // IBillingUseCase.Listener
    override fun onPurchase(isPremium: Boolean) {}
}