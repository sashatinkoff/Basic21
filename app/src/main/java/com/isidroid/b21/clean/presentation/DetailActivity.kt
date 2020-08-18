package com.isidroid.b21.clean.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val data = with(intent.extras) {
            this?.keySet()?.map { "$it=${this.get(it)}\n" }
        }

        val username = intent.extras?.getString("name")

        Timber.e("MessagingService username=$username")
        Timber.i(
            "MessagingService onCreate action=${intent.action}, " +
                    "\nextras=$data"
        )

        finishAfterTransition()
    }
}