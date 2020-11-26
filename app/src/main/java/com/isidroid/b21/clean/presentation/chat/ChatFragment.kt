package com.isidroid.b21.clean.presentation.chat

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.isidroid.b21.R
import com.isidroid.b21.appComponent
import com.isidroid.b21.clean.presentation.main.MainActivity
import com.isidroid.b21.clean.presentation.service.MessageService
import com.isidroid.b21.ext.doOnEnter
import com.isidroid.b21.ext.enable
import com.isidroid.b21.ext.observe
import com.isidroid.b21.models.db.Account
import com.isidroid.b21.models.dto.Message
import com.isidroid.b21.utils.core.BindFragment
import kotlinx.android.synthetic.main.fragment_chat.*
import timber.log.Timber

class ChatFragment : BindFragment(layoutRes = R.layout.fragment_chat) {
    private val viewModel by viewModels<ChatViewModel> { viewModelFactory }
    private val adapter = ChatAdapter()
    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, data: Intent?) {
            val message = data?.getSerializableExtra("message") as? Message ?: return
            onNewMessage(message)
        }
    }

    override fun onAttach(context: Context) {
        appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onResume() {
        super.onResume()
        requireActivity().registerReceiver(receiver, IntentFilter(MessageService.ACTION_MESSAGE))
    }

    override fun onPause() {
        super.onPause()
        requireActivity().unregisterReceiver(receiver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_leave -> viewModel.leave()
            }
            true
        }

        recyclerView.adapter = adapter
        viewModel.create()

        inputName.doOnTextChanged { text, _, _, _ -> buttonSend.enable(!text.isNullOrEmpty()) }
        inputName.doOnEnter { sendMessage() }
        buttonSend.setOnClickListener { sendMessage() }
    }

    private fun sendMessage() {
        val message = inputName.text.toString()
        buttonSend.enable(false)
        inputName.enable(false)
        viewModel.send(message)
    }

    // Live data
    override fun onCreateViewModel() {
        observe(viewModel.state) { state ->
            when (state) {
                ChatViewModel.State.OnMessageSent -> onMessageSent()
                is ChatViewModel.State.OnReady -> onReady(state.account)
                ChatViewModel.State.OnLeave -> onLeave()
            }
        }
    }

    private fun onLeave() {
        (requireActivity() as MainActivity).openLogin()
    }

    private fun onReady(account: Account) {
        adapter.update(account)
    }

    private fun onMessageSent() {
        inputName.setText("")
        inputName.enable(true)
    }

    fun onNewMessage(message: Message) {
        lifecycleScope.launchWhenResumed {
            adapter.items.add(0, message)
            adapter.notifyItemInserted(0)
            recyclerView.layoutManager?.scrollToPosition(0)
        }
    }

    companion object {
        val singleInstance by lazy { ChatFragment() }
    }
}