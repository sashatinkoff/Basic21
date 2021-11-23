package com.isidroid.b21.clean.presentation

import androidx.lifecycle.MutableLiveData
import com.isidroid.b21.clean.domain.LotteryUseCase
import com.isidroid.b21.models.dto.StatisticDto
import com.isidroid.b21.utils.CoroutineViewModel
import timber.log.Timber
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val lotteryUseCase: LotteryUseCase
) : CoroutineViewModel() {
    sealed class State {
        data class SingleStatistics(val first: List<StatisticDto>, val second: List<StatisticDto>) :
            State()
    }

    val state = MutableLiveData<State>()

    fun single() = io(
        doWork = {
            val result = lotteryUseCase.singleStatistics("winners2.txt")

            State.SingleStatistics(
                first = result.first.take(4).map { it.statistic(result.first.size) },
                second = result.second.take(4).map { it.statistic(result.second.size) }
            )
        },
        onComplete = { state.value = it!! }
    )

}