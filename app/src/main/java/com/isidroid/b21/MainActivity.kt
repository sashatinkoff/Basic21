package com.isidroid.b21

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.isidroid.b21.di.appComponent
import com.isidroid.b21.di.viewmodel.ViewModelFactory
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject lateinit var viewModelFactory: ViewModelFactory
    val viewModel by viewModels<MainViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent().inject(this)

        setContentView(R.layout.activity_main)
        viewModel.create(caller = this)

        supportFragmentManager.beginTransaction().apply {
            add(R.id.container, MainFragment())
            commitAllowingStateLoss()
        }
    }
}