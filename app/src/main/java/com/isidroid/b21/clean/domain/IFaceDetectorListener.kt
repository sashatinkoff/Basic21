package com.isidroid.a18.clean.domain

import android.graphics.Bitmap
import com.google.mlkit.vision.face.FaceLandmark
import com.isidroid.b21.clean.data.YLandmark

interface IFaceDetectorListener {
    fun onStartDetection(){}
    fun onFacesFound(count: Int){}
    fun onLandmarkFound(landmark: YLandmark, bitmap: Bitmap){}
    fun onFaceDetectionError(t: Throwable){}
    fun onBitmapFound(iBitmap: Bitmap){}
    fun onLandmark2(allLandmarks: List<FaceLandmark>, bitmap: Bitmap)
}