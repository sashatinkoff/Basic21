package com.isidroid.b21.clean.domain

import com.isidroid.b21.models.db.Account

interface IChatUseCase {
    fun sendMessage(account: Account, message: String)
}