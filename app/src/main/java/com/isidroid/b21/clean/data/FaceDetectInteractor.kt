package com.isidroid.b21.clean.data

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.PointF
import com.google.android.gms.tasks.Tasks
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceContour
import com.google.mlkit.vision.face.FaceDetector
import com.google.mlkit.vision.face.FaceLandmark
import com.isidroid.b21.clean.domain.IFaceDetectUseCase
import com.isidroid.a18.clean.domain.IFaceDetectorListener
import timber.log.Timber
import javax.inject.Inject

class FaceDetectInteractor @Inject constructor(
    private val firebaseVisionDetector: FaceDetector
) : IFaceDetectUseCase {
    private lateinit var image: InputImage
    private var listener: IFaceDetectorListener? = null
    private var original: Bitmap? = null

    override fun listener(listener: IFaceDetectorListener?) = apply {
        this.listener = listener
    }

    override fun fromFile(filepath: String, forceScale: Boolean) = apply {
        fromBitmap(BitmapFactory.decodeFile(filepath), forceScale)
    }

    override fun fromBitmap(bitmap: Bitmap, forceScale: Boolean) = apply {
        original = if (forceScale) bitmap else bitmap
        image = InputImage.fromBitmap(original!!, 0)
    }

    override fun detect(searchForCorrectOrientation: Boolean): Pair<YLandmark, Bitmap>? {
        listener?.onStartDetection()
        var angle = 0
        while (angle < 4) {
            Timber.i("original=${original?.width}x${original?.height}")


            val task = firebaseVisionDetector.process(image)
            val faces = Tasks.await(task)

            if (faces.isNotEmpty()) {
                val landmarks = mutableListOf<YLandmark>()
                val bitmap = image.bitmapInternal!!

                Timber.i("bitmapInternal=${original?.width}x${original?.height}")

                listener?.onBitmapFound(bitmap)

                faces.maxBy { with(it.boundingBox) { width() * height() } }
                    ?.let { face ->
                        val landmark = YLandmark()
                        landmark.headAngle = -face.headEulerAngleZ
                        landmark.canvas =
                            PointF(bitmap.width.toFloat(), bitmap.height.toFloat())


                        face.allLandmarks.first {
                            it.landmarkType == FaceLandmark.RIGHT_EYE
                        }.let { landmark.leftEye = PointF(it.position.x, it.position.y) }

                        face.allLandmarks.first {
                            it.landmarkType == FaceLandmark.LEFT_EYE
                        }.let { landmark.rightEye = PointF(it.position.x, it.position.y) }


                        face.getContour(FaceContour.FACE)!!
                            .points
                            .forEach {
                                landmark.addFacePoint(it.x, it.y)
                            }



                        landmark.box = face.boundingBox
                        landmarks.add(landmark)
                    }

                val landmark = landmarks.filter { it.valid() }.maxBy { it.weight() }
                landmarks.sortedByDescending { it.weight() }.forEach { Timber.i("$it") }
                landmark ?: throw Exception("No landmark found")

//                original?.let { listener?.onLandmarkFound(landmark, it) }

                return Pair(landmark, bitmap)
            }

            if (!searchForCorrectOrientation) break

            angle++

        }

        return null
    }
}