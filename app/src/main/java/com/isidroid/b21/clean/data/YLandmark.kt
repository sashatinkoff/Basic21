package com.isidroid.b21.clean.data

import android.graphics.PointF
import android.graphics.Rect
import com.google.mlkit.vision.face.FaceLandmark
import kotlin.math.max
import kotlin.math.min

class YLandmark {
    var rightEye = PointF(0f, 0f)
    var leftEye = PointF(0f, 0f)
    var facePoints = mutableListOf<PointF>()
    var box = Rect()
    var canvas = PointF()
    var headAngle = 0f
    val a = mutableListOf<FaceLandmark>()

    fun extendedBox() = Rect().apply {
        left = min(box.left, left().toInt())
        right = max(box.right, right().toInt())
        top = max(box.top, top().toInt())
        bottom = min(box.bottom, bottom().toInt())
    }

    fun top() = facePoints.maxBy { it.y }!!.y
    fun bottom() = facePoints.minBy { it.y }!!.y
    fun left(): Float = facePoints.maxBy { it.x }!!.x
    fun right() = facePoints.minBy { it.x }!!.x


    fun addFacePoint(x: Float?, y: Float?) {
        if (x == null || y == null) return
        facePoints.add(PointF(x, y))
    }

    fun weight() = with(extendedBox()) { bottom - top * right - left }
    fun valid() = true //facePoints.isNotEmpty()

    override fun toString(): String {
        return "YLandmark(rightEye=$rightEye, leftEye=$leftEye, box=$box, canvas=$canvas, headAngle=$headAngle)"
    }

    fun addFacePoint(x: FaceLandmark?) {
        x ?: return
        a.add(x)
    }


}