package com.isidroid.b21.sample.clean.presentation

import androidx.lifecycle.MutableLiveData
import com.isidroid.b21.CoroutineViewModel
import com.isidroid.b21.sample.Post
import com.isidroid.b21.sample.clean.domain.PostListUseCase
import javax.inject.Inject

class MainViewModel @Inject constructor(private val useCase: PostListUseCase) : CoroutineViewModel() {
    var data = MutableLiveData<List<Post>>()

    fun create() = io(
        doWork = { useCase.get() },
        onComplete = { data.value = it }
    )
}