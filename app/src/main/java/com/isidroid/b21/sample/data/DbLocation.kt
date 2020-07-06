package com.isidroid.b21.sample.data

import io.realm.RealmModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.util.*

@RealmClass
open class DbLocation(
    @PrimaryKey var uid: String = UUID.randomUUID().toString().substring(0, 5),
    var latitude: Double = 0.0,
    var longitude: Double = 0.0,
    var createdAt: Date = Date()
) : RealmModel