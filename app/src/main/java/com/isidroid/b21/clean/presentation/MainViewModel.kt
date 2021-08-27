package com.isidroid.b21.clean.presentation

import android.net.Uri
import androidx.core.content.FileProvider
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Tasks
import com.google.firebase.messaging.FirebaseMessaging
import com.isidroid.b21.App
import com.isidroid.b21.di.PictureAuthority
import com.isidroid.b21.network.apis.ApiSession
import com.isidroid.b21.utils.CoroutineViewModel
import java.io.File
import java.util.*
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val api: ApiSession,
    @PictureAuthority private val authority: String
) : CoroutineViewModel() {
    sealed class State {
        data class OnReady(val uri: Uri?) : State()
    }

    val state = MutableLiveData<State>()


    fun recreate() = io(
        doWork = {
//            FirebaseMessaging.getInstance().isAutoInitEnabled = false
//            Timber.i("==> Current token isAutoInit=${FirebaseMessaging.getInstance().isAutoInitEnabled}")
//            Timber.e(token())
//
////            FirebaseInstanceId.getInstance().deleteInstanceId()
//
//            with(FirebaseMessaging.getInstance().deleteToken()) { Tasks.await(this) }
//                .also {
//                    Timber.i("Deleted")
//                }
//
//            Timber.i("==> Refreshed token")
//            Timber.e(token())
        }
    )

    fun load(videoMp4: String) = io(
        doWork = {
            val responseBody = api.downloadFile(videoMp4).body()
            val file = File(App.instance.cacheDir, "${UUID.randomUUID().toString().take(5)}.mp4")

            file.saveFile(responseBody)

            FileProvider.getUriForFile(App.instance, authority, file)
        },
        onComplete = {
            state.value = State.OnReady(it)
        }
    )
}