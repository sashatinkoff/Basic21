package com.isidroid.b21.clean.data

import com.google.firebase.messaging.FirebaseMessaging
import com.isidroid.b21.clean.domain.IAccountUseCase
import com.isidroid.b21.clean.presentation.service.MessageService
import com.isidroid.b21.models.db.Account
import com.isidroid.b21.models.settings.Settings
import io.realm.Realm
import io.realm.kotlin.deleteFromRealm
import timber.log.Timber

class AccountInteractor : IAccountUseCase {
    override fun findOrCreate() = with(Realm.getDefaultInstance()) {
        (where(Account::class.java).equalTo("uid", Settings.sessionKey.orEmpty())
            .findFirst()
            ?.let { copyFromRealm(it) } ?: Account())
            .also { close() }
    }

    override fun save(account: Account) {
        with(Realm.getDefaultInstance()) {
            FirebaseMessaging.getInstance().subscribeToTopic(MessageService.PUSH_TOPIC)
            executeTransaction { it.insertOrUpdate(account) }
            Settings.sessionKey = account.uid
            close()
        }
    }

    override fun leave() {
        with(Realm.getDefaultInstance()) {

            executeTransaction {
                where(Account::class.java).equalTo("uid", Settings.sessionKey.orEmpty())
                    .findFirst()?.deleteFromRealm()
            }
            close()

            Settings.sessionKey = null
            FirebaseMessaging.getInstance().unsubscribeFromTopic(MessageService.PUSH_TOPIC)
            Timber.i("sdfsdfsdf unsubscribe ")
        }

    }
}