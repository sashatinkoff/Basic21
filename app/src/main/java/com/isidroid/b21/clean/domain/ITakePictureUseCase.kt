package com.isidroid.b21.clean.domain

import android.content.Intent
import android.net.Uri
import com.isidroid.b21.clean.data.picturehandler.ImageInfo

interface ITakePictureUseCase {
    var forceRotate: Boolean
    var debugCallback: ((String) -> Unit)?

    fun config(
        forceRotate: Boolean? = null,
        debugCallback: ((String) -> Unit)? = null
    ): ITakePictureUseCase = apply {
        forceRotate?.let { this.forceRotate = it }
        debugCallback?.let { this.debugCallback = it }
    }

    fun getFromGallery(intent: Intent?): List<ImageInfo>
    fun getFromGallery(uris: List<Uri>?): List<ImageInfo>
    fun processPhoto(request: IPictureHandlerUseCase.Request?): ImageInfo
    fun rotate(imageInfo: ImageInfo?): String?
}