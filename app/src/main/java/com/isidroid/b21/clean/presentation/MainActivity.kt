package com.isidroid.b21.clean.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseUser
import com.isidroid.b21.R
import com.isidroid.b21.appComponent
import com.isidroid.b21.ext.enable
import com.isidroid.b21.ext.hideSoftKeyboard
import com.isidroid.b21.ext.observe
import com.isidroid.b21.utils.core.BindActivity
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import java.lang.StringBuilder

@SuppressLint("SetTextI18n")
class MainActivity : BindActivity(layoutRes = R.layout.activity_main) {
    private val viewModel by viewModels<MainViewModel> { viewModelFactory }
    private val inputs by lazy {
        arrayOf(
            pin1View,
            pin2View,
            pin3View,
            pin4View,
            pin5View,
            pin6View
        ).map { it.findViewById<TextInputEditText>(R.id.input) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)
        super.onCreate(savedInstanceState)

        phoneInput.setText("+79028391999")
        phoneInput.setText("+75555555555")


        inputs.forEachIndexed { index, view ->
            try {
                inputs[index + 1]
            } catch (e: Throwable) {
                null
            }?.let {
                view.doOnTextChanged { text, _, _, _ ->
                    if (text?.length == 1) it.requestFocus()
                    else {
                        buttonSubmit.requestFocus()
                        hideSoftKeyboard()
                    }
                }
            }
        }

        buttonSubmit.setOnClickListener { requestCode() }
    }

    private fun requestCode(reset: Boolean = false) {
        buttonSubmit.enable(false)
        infoTextView.text = ""
        viewModel.requestCode(this, phone = phoneInput.text.toString(), reset = reset)
    }

    private fun confirmCode() {
        val builder = StringBuilder()
        inputs.forEach { builder.append(it.text.toString()) }

        viewModel.confirm(builder.toString())
    }

    override fun onCreateViewModel() {
        observe(viewModel.state) { state ->
            when (state) {
                is MainViewModel.State.OnInfo -> onInfo(state.message)
                is MainViewModel.State.OnError -> onError(state.t)
                is MainViewModel.State.OnComplete -> onComplete(state.user)
                MainViewModel.State.OnCodeSent -> onCodeSent()
            }
        }
    }

    private fun onComplete(user: FirebaseUser) {
        onInfo("onComplete ${user.phoneNumber}")
        buttonSubmit.enable(true)
        buttonSubmit.setOnClickListener { requestCode(reset = true) }
    }

    private fun onCodeSent() {
        onInfo("onCodeSent")
        buttonSubmit.enable(true)
        buttonSubmit.setOnClickListener { confirmCode() }
    }

    private fun onError(t: Throwable) {
        buttonSubmit.enable(true)
        Toast.makeText(this, t.message, Toast.LENGTH_SHORT).show()
        onInfo(t.message)
    }

    private fun onInfo(message: String?) {
        message ?: return
        Timber.i("onInfoReceived $message")

        val text = infoTextView.text.toString()
        infoTextView.text = "$message+\n\n$text"
    }
}