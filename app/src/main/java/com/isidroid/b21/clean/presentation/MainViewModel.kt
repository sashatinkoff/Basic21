package com.isidroid.b21.clean.presentation

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.MutableLiveData
import com.isidroid.b21.clean.data.BitmapHelper
import com.isidroid.b21.clean.domain.IFaceDetectUseCase
import com.isidroid.b21.clean.domain.IPictureHandlerUseCase
import com.isidroid.b21.utils.CoroutineViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val pictureHandlerUseCase: IPictureHandlerUseCase,
    private val faceDetectUseCase: IFaceDetectUseCase
) :
    CoroutineViewModel() {
    sealed class State {
        data class OnBitmap(val bitmap: Bitmap) : State()
        data class OnError(val t: Throwable) : State()
    }

    val state = MutableLiveData<State>()

    fun pick(
        caller: Any,
        contentType: String,
        data: HashMap<String, String>? = null,
        isMultiple: Boolean
    ) {
        pictureHandlerUseCase.pickGallery(caller, contentType, data, isMultiple = isMultiple)
    }

    fun onResult(resultCode: Int, requestCode: Int, data: Intent?) = io(
        doWork = {
            if (resultCode != Activity.RESULT_OK) throw Exception("PictureNotSelectedException")

            val pictureResult = pictureHandlerUseCase.result(requestCode, data)
                ?: throw Exception("PictureNotSelectedException")

            val bitmap = BitmapFactory.decodeFile(pictureResult.list.first().localPath)

            val result = faceDetectUseCase
                .fromBitmap(bitmap)
                .detect()!!

            BitmapHelper.drawMesh(result.second, result.first)
        },
        onComplete = { state.value = State.OnBitmap(it!!) },
        onError = { state.value = State.OnError(it) }
    )
}