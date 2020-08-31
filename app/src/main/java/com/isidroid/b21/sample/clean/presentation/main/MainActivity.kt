package com.isidroid.b21.sample.clean.presentation.main

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.isidroid.b21.R
import com.isidroid.b21.di.appComponent
import com.isidroid.b21.sample.clean.presentation.main.exolist.scroll
import com.isidroid.b21.utils.BindActivity
import com.isidroid.b21.utils.views.adapters.CoreBindAdapter.Companion.VIEW_TYPE_NORMAL
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber


class MainActivity : BindActivity(layoutRes = R.layout.activity_main) {
    private val adapter by lazy { Adapter(this) }

    private val videos by lazy {
        val response = resources.openRawResource(R.raw.response)
            .bufferedReader()
            .use { it.readText() }

        val data = Gson().fromJson<Response>(
            response,
            object : TypeToken<Response>() {}.type
        ).categories.first().videos
        data.filter { it.isValid }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        appComponent().inject(this)
        super.onCreate(savedInstanceState)

        recyclerView.scroll(adapter)
        adapter.insert(videos)
    }

    override fun onDestroy() {
        adapter.destroy()
        super.onDestroy()
    }
}