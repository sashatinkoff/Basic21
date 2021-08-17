package com.isidroid.b21.clean.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.navArgs
import com.isidroid.b21.R
import com.isidroid.b21.databinding.ActivityDetailsBinding
import com.isidroid.b21.utils.base.BindActivity
import timber.log.Timber

class DetailsActivity :
    BindActivity<ActivityDetailsBinding>(layoutRes = R.layout.activity_details) {
    private val args: DetailsActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Toast.makeText(this, "${args.book}", Toast.LENGTH_SHORT).show()
    }
}