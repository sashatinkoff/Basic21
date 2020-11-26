package com.isidroid.b21.clean.presentation.chat

import android.text.format.DateUtils
import android.view.Gravity
import androidx.databinding.ViewDataBinding
import com.isidroid.b21.R
import com.isidroid.b21.databinding.ItemChatBinding
import com.isidroid.b21.models.db.Account
import com.isidroid.b21.models.dto.Message
import com.isidroid.b21.utils.views.YSpan
import com.isidroid.b21.utils.views.adapters.CoreBindAdapter

class ChatAdapter : CoreBindAdapter<Message>() {
    private var account: Account? = null

    override fun resource(viewType: Int) = R.layout.item_chat
    override fun onBindHolder(binding: ViewDataBinding, position: Int) {
        (binding as? ItemChatBinding)?.bind(items[position], account)
    }

    fun update(account: Account) {
        this.account = account
        notifyDataSetChanged()
    }
}

private fun ItemChatBinding.bind(message: Message, account: Account?) = apply {
    titleView.text = YSpan(root.context)
        .append(message.user)
        .append(" ")
        .append(DateUtils.getRelativeTimeSpanString(message.date.time).toString())
        .build()

    textView.text = message.text

    container.gravity = if (message.user == account?.name) Gravity.END else Gravity.START
}