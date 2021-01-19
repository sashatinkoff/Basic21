package com.isidroid.b21.clean.presentation.editorjs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.isidroid.b21.R

class EJsAdapter(private val listener: Listener) : RecyclerView.Adapter<ViewHolder>() {
    private lateinit var layoutInflater: LayoutInflater
    private val items = mutableListOf<EditorBlock>()

    override fun getItemViewType(position: Int) = items[position].type.viewType

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val resourceId = when (viewType) {
            EditorBlock.Type.HEADER.viewType -> R.layout.item_rich_header
            EditorBlock.Type.PARAGRAPH.viewType -> R.layout.item_rich_paragraph
            EditorBlock.Type.IMAGE.viewType -> R.layout.item_rich_image
            EditorBlock.Type.HIGH_LIGHT.viewType -> R.layout.item_rich_highlight
            EditorBlock.Type.QUOTE.viewType -> R.layout.item_rich_quote
            EditorBlock.Type.EMBED.viewType -> R.layout.item_rich_embed
            else -> 0
        }

        if (!::layoutInflater.isInitialized) layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(resourceId, parent, false), listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        when (item.type.viewType) {
            EditorBlock.Type.HEADER.viewType -> holder.header(item.data)
            EditorBlock.Type.PARAGRAPH.viewType -> holder.paragraph(item.data)
            EditorBlock.Type.IMAGE.viewType -> holder.image(item.data)
            EditorBlock.Type.HIGH_LIGHT.viewType -> holder.highLight(item.data)
            EditorBlock.Type.QUOTE.viewType -> holder.quote(item.data)
            EditorBlock.Type.EMBED.viewType -> holder.embed(item.data)
        }
    }

    override fun getItemCount() = items.size
    fun insert(blocks: List<EditorBlock>) {
        items.clear()
        items.addAll(blocks)
        notifyDataSetChanged()
    }

    interface Listener {}
}

open class ViewHolder(itemView: View, private val listener: EJsAdapter.Listener) :
    RecyclerView.ViewHolder(itemView) {
    fun header(data: EditorBlockData) { }
    fun paragraph(data: EditorBlockData) {}
    fun image(data: EditorBlockData) {}
    fun highLight(data: EditorBlockData) {}
    fun quote(data: EditorBlockData) {}
    fun embed(data: EditorBlockData) {}
}