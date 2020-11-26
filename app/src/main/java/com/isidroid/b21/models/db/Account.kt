package com.isidroid.b21.models.db

import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.internal.Keep
import java.util.*

@RealmClass
@Keep
open class Account(
    @PrimaryKey var uid: String = UUID.randomUUID().toString(),
    var name: String = ""
) : RealmModel {
}