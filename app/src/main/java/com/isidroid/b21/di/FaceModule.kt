package com.isidroid.b21.di

import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetector
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.isidroid.b21.clean.data.FaceDetectInteractor
import com.isidroid.b21.clean.domain.IFaceDetectUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object FaceModule {
    @JvmStatic
    @Singleton
    @Provides
    fun provideFirebaseVisionFaceDetectorOptions(): FaceDetectorOptions =
        FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
            .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
            .build()

    @JvmStatic
    @Singleton
    @Provides
    fun provideVisionFaceDetector(options: FaceDetectorOptions): FaceDetector =
        FaceDetection.getClient(options)

    @JvmStatic
    @Singleton
    @Provides
    fun provideFaceDetectorUseCase(firebaseVisionDetector: FaceDetector): IFaceDetectUseCase =
        FaceDetectInteractor(firebaseVisionDetector)
}