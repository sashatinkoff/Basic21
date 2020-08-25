package com.isidroid.b21.clean.domain

import android.graphics.Bitmap
import com.isidroid.b21.clean.data.YLandmark
import com.isidroid.a18.clean.domain.IFaceDetectorListener

interface IFaceDetectUseCase {
    fun listener(listener: IFaceDetectorListener?): IFaceDetectUseCase
    fun fromFile(filepath: String, forceScale: Boolean = false): IFaceDetectUseCase
    fun fromBitmap(bitmap: Bitmap, forceScale: Boolean = true): IFaceDetectUseCase
    fun detect(searchForCorrectOrientation: Boolean = false): Pair<YLandmark, Bitmap>?
}