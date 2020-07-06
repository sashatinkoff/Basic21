package com.isidroid.b21.sample.clean.domain

import android.location.Location
import com.isidroid.b21.sample.data.DbLocation

interface DbLocationUseCase {
    fun save(locationResult: Location)
    fun list(): List<DbLocation>
    fun clear()
}