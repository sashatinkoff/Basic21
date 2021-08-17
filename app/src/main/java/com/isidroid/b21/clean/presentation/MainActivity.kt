package com.isidroid.b21.clean.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.isidroid.b21.R
import com.isidroid.b21.appComponent
import com.isidroid.b21.databinding.ActivityMainBinding
import com.isidroid.b21.utils.base.BindActivity
import com.isidroid.b21.utils.core.CoreBindActivity
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : BindActivity<ActivityMainBinding>(layoutRes = R.layout.activity_main) {
    private val viewModel by viewModels<MainViewModel> { viewModelFactory }
    val navController by lazy { findNavController(R.id.nav_host_fragment) }

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
    }

    override fun onBackPressed() {
        super.onBackPressed()

        navController.navigate(R.id.registerFragment)
    }

    override fun onFragmentResumed(fr: Fragment) {
        super.onFragmentResumed(fr)

        Timber.i(
            "dsfsdfdsf onFragmentResumed ${fr.javaClass.simpleName}, " +
                    "backStackEntryCount=${supportFragmentManager.backStackEntryCount}, " +
                    "fragments=${supportFragmentManager.fragments.size}"
        )
    }
}