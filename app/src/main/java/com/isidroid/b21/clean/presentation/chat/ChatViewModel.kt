package com.isidroid.b21.clean.presentation.chat

import androidx.lifecycle.MutableLiveData
import com.isidroid.b21.clean.domain.IAccountUseCase
import com.isidroid.b21.clean.domain.IChatUseCase
import com.isidroid.b21.models.db.Account
import com.isidroid.b21.models.settings.Settings
import com.isidroid.b21.utils.CoroutineViewModel
import io.realm.Realm
import javax.inject.Inject

class ChatViewModel @Inject constructor(
    private val chatUseCase: IChatUseCase,
    private val accountUseCase: IAccountUseCase
) : CoroutineViewModel() {
    private lateinit var account: Account

    sealed class State {
        object OnMessageSent : State()
        object OnLeave : State()
        data class OnReady(val account: Account) : State()
    }

    val state = MutableLiveData<State?>()

    fun create() = io(
        doWork = {
            this.account = accountUseCase.findOrCreate()
            chatUseCase.sendMessage(account, "${account.name} joined to this chatroom")
        },
        onComplete = { state.value = State.OnReady(account) }
    )

    fun send(message: String) = io(
        doWork = { chatUseCase.sendMessage(account, message) },
        onComplete = { state.value = State.OnMessageSent }
    )

    fun leave() = io(
        doWork = {
            accountUseCase.leave()
            chatUseCase.sendMessage(account, "${account.name} left to this chatroom")
        },
        onComplete = { state.value = State.OnLeave }
    )
}