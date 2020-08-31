package com.isidroid.b21.sample.clean.presentation.main

import com.google.gson.annotations.SerializedName

data class Response(@SerializedName("categories") val categories: List<CategoriesResponse>)
data class CategoriesResponse(@SerializedName("videos") val videos: List<VideoDto>)

data class VideoDto(
    @SerializedName("description") val description: String?,
    @SerializedName("thumb") val thumb: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("sources") val sources: List<String>?
) {
    val url
        get() = sources!!.first()

    val isValid
        get() = !sources.isNullOrEmpty()
}