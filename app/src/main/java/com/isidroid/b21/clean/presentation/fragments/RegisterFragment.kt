package com.isidroid.b21.clean.presentation.fragments

import com.isidroid.b21.R
import com.isidroid.b21.clean.presentation.Book

class RegisterFragment : BaseFragment2() {

    override fun onButtonClick() {
        val action = RegisterFragmentDirections.actionRegisterFragmentToBookListFragment()

        navController.navigate(action)
    }
}