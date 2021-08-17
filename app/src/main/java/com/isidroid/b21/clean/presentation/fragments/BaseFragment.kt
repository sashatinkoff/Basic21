package com.isidroid.b21.clean.presentation.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.isidroid.b21.R
import com.isidroid.b21.clean.presentation.MainActivity
import com.isidroid.b21.databinding.FragmentBinding
import com.isidroid.b21.utils.base.BindFragment
import kotlinx.android.synthetic.main.fragment.*
import timber.log.Timber

abstract class BaseFragment2 : BindFragment<FragmentBinding>(layoutRes = R.layout.fragment) {
    open val title: String = javaClass.simpleName
    val navController by lazy { (requireActivity() as MainActivity).navController }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textView.text = title
        button.setOnClickListener { onButtonClick() }
    }

    open fun onButtonClick() {
        Toast.makeText(requireActivity(), "$title clicked", Toast.LENGTH_SHORT).show()
    }
}