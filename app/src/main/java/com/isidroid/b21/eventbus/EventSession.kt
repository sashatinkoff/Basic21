package com.isidroid.b21.eventbus

import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * Implement a listener in any class such as Activity
 * Create a bus in this class and register / unregister it in onCreate / onDestroy
 *
 * private val sessionEvent = EventSession(this)
 *
 * override fun onCreate(savedInstanceState: Bundle?) { sessionEvent.subscribe() }
 * override fun onDestroy() { sessionEvent.onDestroy() }
 *
 * Send an event in any place of the app by EventSession.onSignOut()
 */
class EventSession(private val listener: Listener) : BaseEventBus {
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventSignedIn(event: OnEventSignIn) {
        listener.onSignedIn(userId = event.id)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventSignedOut(event: OnEventSignOut) {
        listener.onSignedOut()
    }

    // classes
    class OnEventSignIn(val id: String?)
    class OnEventSignOut


    interface Listener {
        fun onSignedIn(userId: String? = null)
        fun onSignedOut()
    }

    companion object {
        fun onSignIn(id: String?) = BaseEventBus.send { it.post(OnEventSignIn(id)) }
        fun onSignOut() = BaseEventBus.send { it.post(OnEventSignOut()) }
    }
}