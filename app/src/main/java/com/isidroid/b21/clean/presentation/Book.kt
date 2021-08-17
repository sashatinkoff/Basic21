package com.isidroid.b21.clean.presentation

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize data class Book(val name: String, val author: String, val year: Int) : Parcelable