package com.isidroid.b21.clean.presentation

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.isidroid.b21.R
import com.isidroid.b21.di.appComponent
import com.isidroid.b21.ext.observe
import com.isidroid.b21.ext.permission
import com.isidroid.b21.utils.BindActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BindActivity(layoutRes = R.layout.activity_main), IMainView {
    private val viewModel by viewModels<MainViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent().inject(this)
        super.onCreate(savedInstanceState)

        permission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
            onGranted = {})

        button.setOnClickListener {
            viewModel.pick(
                caller = this,
                contentType = "image/*",
                isMultiple = false
            )
        }
    }

    override fun onCreateViewModel() {
        observe(viewModel.state) { onState(it) }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        viewModel.onResult(resultCode, requestCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun onState(state: MainViewModel.State?) {
        when (state) {
            is MainViewModel.State.OnError -> Toast.makeText(
                this,
                state.t.message,
                Toast.LENGTH_LONG
            ).show()
            is MainViewModel.State.OnBitmap -> imageView.setImageBitmap(state.bitmap)
        }
    }
}