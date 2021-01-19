package com.isidroid.b21.clean.presentation.editorjs

import com.google.gson.annotations.SerializedName
import java.util.*

data class EjsResponse(@SerializedName("data") val data: EjsData)

data class EjsData(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("author_name") val author: String,
    @SerializedName("logo_url") val logo: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("content") val content: String?,
    @SerializedName("category_name") val category: String?,
    @SerializedName("created_at") val createdAt: Date,
    @SerializedName("slug") val slug: String,
    @SerializedName("editor_content") val editorContent: EditorContent?
)

data class EditorContent(@SerializedName("blocks") val blocks: List<EditorBlock>)
data class EditorBlock(
    @SerializedName("type") private val _type: String,
    @SerializedName("data") val data: EditorBlockData
) {
    enum class Type(val type: String, val viewType: Int) {
        HEADER(type = "header", viewType = 100),
        PARAGRAPH(type = "paragraph", viewType = 101),
        IMAGE(type = "image", viewType = 102),
        HIGH_LIGHT(type = "highlight", viewType = 103),
        QUOTE(type = "quote", viewType = 104),
        EMBED(type = "embed", viewType = 105),
    }

    val type
        get() = Type.values().firstOrNull { it.type == _type } ?: Type.PARAGRAPH
}

data class EditorFile(@SerializedName("url") val url: String)

data class EditorBlockData(
    @SerializedName("text") val text: String,

    // Highlight
    @SerializedName("title") val title: String?,
    @SerializedName("color") val color: String?,

    // Quote
    @SerializedName("caption") val caption: String?,
    @SerializedName("alignment") val alignment: String?,

    // Header
    @SerializedName("level") val level: Int?,

    // Image
    @SerializedName("file") val file: EditorFile?,

    // video
    @SerializedName("service") val service: String?,
    @SerializedName("source") val source: String?,
    @SerializedName("embed") val embed: String?
)

