package com.isidroid.b21.eventbus

import org.greenrobot.eventbus.EventBus

interface BaseEventBus {
    fun subscribe() = try {
        EventBus.getDefault().register(this)
    } catch (t: Throwable) {
    }

    fun unSubscribe() = try {
        EventBus.getDefault().unregister(this)
    } catch (t: Throwable) {
    }

    companion object {
        fun send(callback: (EventBus) -> Unit) = callback(EventBus.getDefault())
    }
}