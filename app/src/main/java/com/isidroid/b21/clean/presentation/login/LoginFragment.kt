package com.isidroid.b21.clean.presentation.login

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import com.isidroid.b21.R
import com.isidroid.b21.appComponent
import com.isidroid.b21.clean.presentation.main.MainActivity
import com.isidroid.b21.ext.doOnEnter
import com.isidroid.b21.ext.enable
import com.isidroid.b21.ext.hideSoftKeyboard
import com.isidroid.b21.ext.observe
import com.isidroid.b21.utils.core.BindFragment
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BindFragment(layoutRes = R.layout.fragment_login) {
    private val viewModel by viewModels<LoginViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        buttonSignin.enable(false)
        inputName.doOnTextChanged { text, _, _, _ -> buttonSignin.enable(!text.isNullOrEmpty()) }
        inputName.doOnEnter { save() }
        buttonSignin.setOnClickListener { save() }

        viewModel.create()
    }

    private fun save() {
        buttonSignin.enable(false)
        requireActivity().hideSoftKeyboard()
        viewModel.save(name = inputName.text.toString())
    }

    // LiveData
    override fun onCreateViewModel() {
        observe(viewModel.state) { state ->
            when (state) {
                LoginViewModel.State.OnSave -> {
                    inputName.setText("")
                    buttonSignin.enable(true)
                    (requireActivity() as MainActivity).openChat()
                }
            }
        }
    }

}