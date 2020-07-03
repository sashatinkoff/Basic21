package com.isidroid.b21

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.isidroid.b21.di.appComponent
import com.isidroid.b21.di.viewmodel.ViewModelFactory
import javax.inject.Inject

class MainFragment : Fragment() {
    @Inject lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: MainViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.create(this)
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}