package com.isidroid.b21.clean.presentation.main

import android.os.Bundle
import com.google.gson.Gson
import com.isidroid.b21.R
import com.isidroid.b21.appComponent
import com.isidroid.b21.clean.presentation.chat.ChatFragment
import com.isidroid.b21.clean.presentation.login.LoginFragment
import com.isidroid.b21.models.dto.Message
import com.isidroid.b21.models.settings.Settings
import com.isidroid.b21.utils.core.BindActivity
import timber.log.Timber
import java.util.*

class MainActivity : BindActivity(layoutRes = R.layout.activity_main) {
    private val loginFragment = LoginFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)
        super.onCreate(savedInstanceState)


        intent.extras?.let {
            if (it.containsKey("user") && it.containsKey("text")) {
                val message = Message(
                    user = it.getString("user", ""),
                    date = Date(it.getLong("google.sent_time")),
                    text = it.getString("text", "")
                )

                ChatFragment.singleInstance.onNewMessage(message)
            }
        }

        if (Settings.sessionKey == null) openLogin()
        else openChat()
    }

    fun openLogin() = openFragment(fragment = loginFragment)
    fun openChat() = openFragment(fragment = ChatFragment.singleInstance)

}