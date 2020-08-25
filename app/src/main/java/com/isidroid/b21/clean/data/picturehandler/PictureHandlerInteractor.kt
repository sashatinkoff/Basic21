package com.isidroid.b21.clean.data.picturehandler

import android.app.Activity
import android.content.Intent
import android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
import android.provider.MediaStore
import androidx.annotation.CallSuper
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.isidroid.a18.clean.data.picturehandler.ImageInfo
import com.isidroid.b21.clean.domain.IPictureHandlerUseCase
import com.isidroid.b21.clean.domain.ITakePictureUseCase
import com.isidroid.b21.di.PictureAuthority
import java.io.File

class PictureHandlerInteractor(
    @PictureAuthority private val authority: String,
    private val takePictureUseCase: ITakePictureUseCase
) : IPictureHandlerUseCase {

    private var request: IPictureHandlerUseCase.Request? = null
    private var data: HashMap<String, String>? = null
    override var codeTakePicture: Int = 300
    override var codePickGallery: Int = 400
    override var debugCallback: ((String) -> Unit)? = null
        set(value) {
            takePictureUseCase.debugCallback = value
            field = value
        }

    override fun config(
        codeTakePicture: Int?,
        codePickGallery: Int?,
        debugCallback: ((String) -> Unit)?,
        forceRotate: Boolean?
    ): IPictureHandlerUseCase = apply {
        super.config(codeTakePicture, codePickGallery, debugCallback, forceRotate)
        takePictureUseCase.config(forceRotate = forceRotate, debugCallback = debugCallback)
    }

    override fun takePicture(
        caller: Any,
        data: HashMap<String, String>?,
        scale: Float?,
        maxSize: Int?
    ) {
        this.data = data
        val activity = caller as? Activity ?: (caller as Fragment).activity
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        request =
            IPictureHandlerUseCase.Request(
                File.createTempFile("temp_image_", ".jpg"),
                scale,
                maxSize
            )

        val photoURI = FileProvider.getUriForFile(activity!!, authority, request!!.file)

        debugCallback?.invoke("takePicture, photiURI=$photoURI, request=$request")

        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
        when (caller) {
            is Activity -> caller.startActivityForResult(intent, codeTakePicture)
            is Fragment -> caller.startActivityForResult(intent, codeTakePicture)
        }
    }


    @CallSuper
    override fun pickGallery(
        caller: Any,
        contentType: String,
        data: HashMap<String, String>?,
        isMultiple: Boolean,
        decorIntent: ((Intent) -> Unit)?
    ) {

        this.data = data
        val intent = Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            .apply {
                flags = FLAG_GRANT_READ_URI_PERMISSION
                type = contentType

                addCategory(Intent.CATEGORY_OPENABLE)
                putExtra(Intent.EXTRA_ALLOW_MULTIPLE, isMultiple)
                decorIntent?.invoke(this)
            }

        debugCallback?.invoke("pickGallery")

        when (caller) {
            is Activity -> caller.startActivityForResult(intent, codePickGallery)
            is Fragment -> caller.startActivityForResult(intent, codePickGallery)
            else -> throw Exception("Can't pickGallery with this caller")
        }
    }

    override fun pickGallery(
        caller: Any,
        isMultiple: Boolean,
        data: HashMap<String, String>?,
        decorIntent: ((Intent) -> Unit)?
    ) =
        pickGallery(caller, "image/*", data, isMultiple, decorIntent)

    override fun result(requestCode: Int, intent: Intent?): IPictureHandlerUseCase.Results? {
        val isPickRequest = requestCode == codePickGallery
        val isTakePictureRequest = requestCode == codeTakePicture

        debugCallback?.invoke("result requestCode=$requestCode, isPickRequest=$isPickRequest, isTakePictureRequest=$isTakePictureRequest")

        val result = when {
            isPickRequest -> takePictureUseCase.getFromGallery(intent)
            isTakePictureRequest -> listOf(takePictureUseCase.processPhoto(request))
            else -> null
        }

        val pictureResult = result?.let { IPictureHandlerUseCase.Results(it, data) }
        debugCallback?.invoke("result pictureResult=$pictureResult")

        return pictureResult
    }

    override fun rotate(path: String) = takePictureUseCase.rotate(ImageInfo(localPath = path))
}