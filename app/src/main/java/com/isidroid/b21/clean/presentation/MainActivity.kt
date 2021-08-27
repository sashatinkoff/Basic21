package com.isidroid.b21.clean.presentation

import android.Manifest
import android.app.ProgressDialog
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.MimeTypes
import com.isidroid.b21.BuildConfig
import com.isidroid.b21.R
import com.isidroid.b21.appComponent
import com.isidroid.b21.databinding.ActivityMainBinding
import com.isidroid.b21.di.PictureAuthority
import com.isidroid.b21.utils.base.BindActivity
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.ResponseBody
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.*
import javax.inject.Inject

private const val VIDEO_MP4 = "https://www.rmp-streaming.com/media/big-buck-bunny-360p.mp4"

class MainActivity : BindActivity<ActivityMainBinding>(layoutRes = R.layout.activity_main) {
    private var progressDialog: ProgressDialog? = null
    private val viewModel by viewModels<MainViewModel> { viewModelFactory }
    @Inject @PictureAuthority lateinit var authority: String

    private var uri: Uri = VIDEO_MP4.toUri()

    private val requestMultiplePermissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { }

    private val shootVideo = registerForActivityResult(ActivityResultContracts.TakeVideo()) { b ->
        preview.setImageBitmap(b)
        Toast.makeText(this,
            b?.let { "Video recorded" } ?: "Some error occurred",
            Toast.LENGTH_SHORT
        ).show()

        onVideoReady()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)

        requestMultiplePermissions.launch(arrayOf(Manifest.permission.CAMERA))
        buttonShoot.setOnClickListener { requestShooting() }
        buttonPick.setOnClickListener { onVideoReady() }
        buttonPlayNetwork.setOnClickListener { playNetwork() }
    }

    override fun onCreateViewModel() {
        viewModel.state.observe(this) { state ->
            when (state) {
                is MainViewModel.State.OnReady -> {
                    progressDialog?.dismiss()

                    uri = state.uri!!
                    onVideoReady()
                }
            }
        }
    }

    private fun playNetwork() {
        progressDialog = ProgressDialog.show(this, "Loading", "", true)
        viewModel.load(VIDEO_MP4)
    }

    private fun requestShooting() {
        val file = File(cacheDir, "${System.currentTimeMillis()}.mp4")
        uri = FileProvider.getUriForFile(this, authority, file)

        shootVideo.launch(uri)
    }

    private fun onVideoReady() {
        videoView.onResume()

        val player = SimpleExoPlayer.Builder(this)
            .build()
            .also { videoView.player = it }

        val dataSourceFactory = DefaultDataSourceFactory(this, BuildConfig.APPLICATION_ID)
        val videoSource = ProgressiveMediaSource.Factory(dataSourceFactory)

        val mediaItem = MediaItem.Builder()
            .setUri(uri)
            .setMimeType(MimeTypes.BASE_TYPE_VIDEO)
            .build()

        player.addMediaSource(videoSource.createMediaSource(mediaItem))
        player.prepare()
        player.playWhenReady = true
    }
}

fun File.saveFile(body: ResponseBody?) {
    if (body == null) return
    var input: InputStream? = null
    try {
        input = body.byteStream()
        val fos = FileOutputStream(absolutePath)
        fos.use { output ->
            val buffer = ByteArray(4 * 1024) // or other buffer size
            var read: Int
            while (input.read(buffer).also { read = it } != -1) {
                output.write(buffer, 0, read)
            }
            output.flush()
        }
    } catch (e: Exception) {
    } finally {
        input?.close()
    }
}