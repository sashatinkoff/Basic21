package com.isidroid.b21.clean.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.isidroid.b21.R
import com.isidroid.b21.appComponent
import com.isidroid.b21.clean.presentation.editorjs.EjsResponse
import com.isidroid.b21.utils.core.BindActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BindActivity(layoutRes = R.layout.activity_main) {
    private val viewModel by viewModels<MainViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent.inject(this)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)

        val json = assets.open("dummy_data.json").readBytes().toString(Charsets.UTF_8)
        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .setLenient()
            .create()

        val response: EjsResponse = gson.fromJson(json, object : TypeToken<EjsResponse>() {}.type)

        val html = response.data.content.orEmpty()
        webView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null)
    }
}