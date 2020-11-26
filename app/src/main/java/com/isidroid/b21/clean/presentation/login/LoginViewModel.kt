package com.isidroid.b21.clean.presentation.login

import androidx.lifecycle.MutableLiveData
import com.isidroid.b21.clean.domain.IAccountUseCase
import com.isidroid.b21.models.db.Account
import com.isidroid.b21.models.settings.Settings
import com.isidroid.b21.utils.CoroutineViewModel
import io.realm.Realm
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val accountUseCase: IAccountUseCase

) : CoroutineViewModel() {
    sealed class State {
        object OnSave : State()
    }

    @Volatile
    private lateinit var account: Account
    val state = MutableLiveData<State?>()

    fun create() = io(
        doWork = { accountUseCase.findOrCreate() },
        onComplete = { this.account = it!! }
    )

    fun save(name: String) = io(
        doWork = {
            account.name = name
            accountUseCase.save(account = account)
        },
        onComplete = { state.value = State.OnSave }
    )
}