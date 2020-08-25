package com.isidroid.b21.clean.data

import android.graphics.*


object BitmapHelper {
    fun drawMesh(iBitmap: Bitmap, landmark: YLandmark): Bitmap? {
        val overlay = Bitmap.createBitmap(iBitmap.width, iBitmap.height, iBitmap.config)
        val canvas = Canvas(overlay)

        val matrix = Matrix()
        val bitmap =
            Bitmap.createBitmap(iBitmap, 0, 0, iBitmap.width, iBitmap.height, matrix, false)

        canvas.drawBitmap(bitmap, 0f, 0f, null)

        val pointPaint = Paint().apply { color = Color.RED }
        val pointPaint2 = Paint().apply { color = Color.GREEN }

        canvas.drawCircle(landmark.leftEye.x, landmark.leftEye.y, 10f, pointPaint)
        canvas.drawCircle(landmark.rightEye.x, landmark.rightEye.y, 10f, pointPaint)


        landmark.facePoints.forEach {
            canvas.drawCircle(it.x, it.y, 5f, pointPaint2)
        }


//        landmark.a.forEach {
//            val p = Paint().apply { color = Color.RED }
//            canvas.drawCircle(it.position.x, it.position.y, 5f, pointPaint2)
//        }



        canvas.drawRect(landmark.extendedBox(), pointPaint.apply {
            color = Color.CYAN
            style = Paint.Style.STROKE
            strokeWidth = 5f
        })

        return overlay
    }

}