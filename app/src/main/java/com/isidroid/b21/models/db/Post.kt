package com.isidroid.b21.models.db

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Post(val id: Int, val name: String): Parcelable