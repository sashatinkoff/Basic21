package com.isidroid.b21.clean.domain

import com.isidroid.b21.models.db.Account

interface IAccountUseCase {
    fun findOrCreate(): Account
    fun save(account: Account)
    fun leave()
}