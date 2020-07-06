package com.isidroid.b21.sample.clean.presentation.main

import android.location.Location
import androidx.lifecycle.MutableLiveData
import com.isidroid.b21.sample.clean.domain.DbLocationUseCase
import com.isidroid.b21.sample.data.DbLocation
import com.isidroid.b21.utils.CoroutineViewModel
import javax.inject.Inject

class MainViewModel @Inject constructor(private val dbUseCase: DbLocationUseCase) :
    CoroutineViewModel() {

    sealed class State {
        data class ConfirmClear(val size: Int) : State()
        data class Data(val list: List<Location>, val location: Location) : State()
        data class DataPath(val list: List<DbLocation>) : State()
    }

    private val locations = mutableListOf<Location>()
    val state = MutableLiveData<State>()

    fun requestClear() = io(
        doWork = { dbUseCase.list() },
        onComplete = { state.value = State.ConfirmClear(size = it?.size ?: 0) }
    )

    fun clearData() = io(doWork = { dbUseCase.clear() })
    fun onLocation(location: Location) = io(
        doWork = {
            if (locations.lastOrNull() == location) null
            else {
                locations.add(location)
                State.Data(list = locations, location = location)
            }
        },
        onComplete = { it?.let { state.value = it } }
    )

    fun showPath() = io(
        doWork = { dbUseCase.list() },
        onComplete = { state.value = State.DataPath(it!!) })
}