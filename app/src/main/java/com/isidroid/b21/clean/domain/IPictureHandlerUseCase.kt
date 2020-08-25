package com.isidroid.b21.clean.domain

import android.content.Intent
import androidx.annotation.CallSuper
import com.isidroid.a18.clean.data.picturehandler.ImageInfo
import java.io.File

interface IPictureHandlerUseCase {
    var codeTakePicture: Int
    var codePickGallery: Int
    var debugCallback: ((String) -> Unit)?

    @CallSuper
    fun config(
        codeTakePicture: Int? = null,
        codePickGallery: Int? = null,
        debugCallback: ((String) -> Unit)? = null,
        forceRotate: Boolean? = null
    ) = apply {
        codeTakePicture?.let { this.codeTakePicture = it }
        codePickGallery?.let { this.codePickGallery = it }
        debugCallback?.let { this.debugCallback = it }
    }

    fun takePicture(caller: Any, data: HashMap<String, String>?, scale: Float?, maxSize: Int?)

    fun pickGallery(
        caller: Any,
        contentType: String = "",
        data: HashMap<String, String>? = null,
        isMultiple: Boolean = false,
        decorIntent: ((Intent) -> Unit)? = null
    )

    fun pickGallery(
        caller: Any,
        isMultiple: Boolean,
        data: HashMap<String, String>? = null,
        decorIntent: ((Intent) -> Unit)? = null
    )

    fun result(requestCode: Int, intent: Intent? = null): Results?
    fun rotate(path: String): String?

    data class Request(
        val file: File,
        val scale: Float? = null,
        val maxSize: Int? = null
    )

    data class Results(val list: List<ImageInfo>, val data: HashMap<String, String>?)
}