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
        data class Statistics(
            val name: String,
            val first: List<StatisticDto>,
            val second: List<StatisticDto>
        ) : State()
    }

    private val fileName = "winners.txt"
    val state = MutableLiveData<State>()

    fun single() = io(
        doWork = {
            val result = lotteryUseCase.singleStatistics(fileName)

            State.Statistics(
                name = "Single statistic",
                first = result.first.take(8).map { it.statistic },
                second = result.second.take(8).map { it.statistic }
            )
        },
        onComplete = { state.value = it!! }
    )

    fun double() = io(
        doWork = {
            val result = lotteryUseCase.doubleStatistics(fileName)
            State.Statistics(
                name = "Double statistic",
                first = result.first.take(8).map { it.statistic },
                second = result.second.take(8).map { it.statistic }
            )
        },
        onComplete = { state.value = it!! }
    )

}