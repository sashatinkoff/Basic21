package com.isidroid.b21.sample.clean.model

import com.isidroid.b21.realm.YRealm
import com.isidroid.b21.sample.clean.data.Car
import com.isidroid.b21.sample.network.Api
import com.isidroid.b21.sample.network.Post
import io.realm.Realm
import okhttp3.internal.closeQuietly
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class PostRepository @Inject constructor(val api: Api) : IPostRepository {
    override fun list(): List<Post> {
        dp()
        return listOf()
    }

    companion object {
        fun dp() {

            val realm = Realm.getDefaultInstance()
            realm.where(Car::class.java).findAll().let { realm.copyFromRealm(it) }
            realm.close()

            Timber.e(
                "realm_instance=$realm, count=${Realm.getGlobalInstanceCount(Realm.getDefaultConfiguration())}" +
                        " / ${Realm.getLocalInstanceCount(Realm.getDefaultConfiguration())}\n " +
                        "\n on ${Thread.currentThread().name} isClosed=${realm.isClosed}"
            )

        }
    }
}