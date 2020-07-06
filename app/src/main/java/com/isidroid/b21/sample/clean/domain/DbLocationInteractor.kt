package com.isidroid.b21.sample.clean.domain

import android.location.Location
import com.isidroid.b21.sample.data.DbLocation
import com.isidroid.b21.sample.di.YRealm
import io.realm.Sort
import timber.log.Timber
import javax.inject.Inject

class DbLocationInteractor @Inject constructor() : DbLocationUseCase {
    private val realm by lazy { YRealm.realm }

    override fun save(locationResult: Location) {
        val dbLocation = DbLocation(
            latitude = locationResult.latitude,
            longitude = locationResult.longitude
        )

        realm.executeTransaction { realm.insertOrUpdate(dbLocation) }
    }

    override fun list(): List<DbLocation> {
        return realm.where(DbLocation::class.java)
            .sort("createdAt", Sort.ASCENDING)
            .findAll()
            .let { realm.copyFromRealm(it) }
    }

    override fun clear() {
        realm.executeTransaction { realm.deleteAll() }
    }
}