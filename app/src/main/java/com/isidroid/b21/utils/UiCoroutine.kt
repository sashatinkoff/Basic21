package com.isidroid.b21.utils

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import kotlinx.coroutines.*
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

class UiCoroutine(lifecycle: Lifecycle) : CoroutineScope, LifecycleObserver {
    private var job = Job()
    private var dispatcher = Dispatchers.Main + job
    private val jobDispatcher: CoroutineDispatcher = Dispatchers.IO
    private var workJob: Job? = null

    init {
        lifecycle.addObserver(this)
    }

    override val coroutineContext: CoroutineContext
        get() {
            if (job.isCancelled) {
                job = Job()
                dispatcher = Dispatchers.Main + job
            }
            return dispatcher
        }

    fun <T> io(
        doWork: () -> T,
        doBefore: (() -> Unit)? = null,
        onComplete: ((T) -> Unit)? = null,
        onError: ((Throwable) -> Unit)? = null
    ) {
        workJob = launch(dispatcher) {
            try {
                doBefore?.invoke()
                val result = withContext(jobDispatcher) { doWork() }
                onComplete?.invoke(result!!)

            } catch (e: Throwable) {
                Timber.e(e)
                if (!isActive) return@launch
                onError?.invoke(e)
            }
        }
    }

    @OnLifecycleEvent(value = Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        job.cancel(CancellationException())
    }

    fun cancel() = workJob?.cancel()
}