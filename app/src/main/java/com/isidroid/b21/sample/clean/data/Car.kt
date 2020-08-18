package com.isidroid.b21.sample.clean.data

import com.isidroid.b21.realm.DataModel
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class Car(@PrimaryKey var uid: String = "uid", var name: String = ""): DataModel