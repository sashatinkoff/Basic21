package com.isidroid.b21.clean.presentation.editorjs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.isidroid.b21.R
import kotlinx.android.synthetic.main.inc_article_header.view.*

class Ejs(
    private val json: String,
    private val container: ViewGroup,
    listener: EJsAdapter.Listener
) {

    private val adapter = EJsAdapter(listener)
    private lateinit var view: View
    private lateinit var response: EjsResponse

    private val gson = GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        .setLenient()
        .create()

    fun create() {
        response = gson.fromJson(json, object : TypeToken<EjsResponse>() {}.type)
        view = LayoutInflater.from(container.context)
            .inflate(R.layout.inc_article_header, container, true)

        createHeader()
        createEditorContent()
    }

    private fun createHeader() {
        response.data.logo?.let { Glide.with(view).load(it).into(view.imageView) }
        view.titleView.text = response.data.name
    }

    private fun createEditorContent() {
        val blocks = response.data.editorContent?.blocks ?: return createContent()
        view.recyclerView.visibility = View.VISIBLE
        view.recyclerView.adapter = adapter
        adapter.insert(blocks)
    }

    private fun createContent() {
        
    }

}