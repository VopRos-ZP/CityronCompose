package ru.cityron.presentation.screens.scheduler

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.cityron.domain.repository.ConfRepository
import ru.cityron.domain.usecase.GetM3SchedUseCase
import ru.cityron.presentation.mvi.MviViewModel
import javax.inject.Inject

@HiltViewModel
class SchedulersViewModel @Inject constructor(
    private val confRepository: ConfRepository,
    private val getM3SchedUseCase: GetM3SchedUseCase
) : MviViewModel<SchedulersViewState, SchedulersViewIntent>() {

    override fun intent(intent: SchedulersViewIntent) {
        when (intent) {
            is SchedulersViewIntent.Launch -> launch()
            is SchedulersViewIntent.OnCheckedChange -> onCheckedChange(intent.sched, intent.value)
        }
    }

    private fun launch() {
        updateState({ copy() }, SchedulersViewState())
        scope.launch {
            while (true) {
                try {
                    val sched = getM3SchedUseCase()
                    updateState {
                        copy(
                            tasks = listOf(
                                sched.task0, sched.task1, sched.task2, sched.task3,
                                sched.task4, sched.task5, sched.task6, sched.task7,
                                sched.task8, sched.task9
                            ).mapIndexed { i, t -> t.copy(i = i) }
                        )
                    }
                } catch (_: Exception) {
                }
            }
        }
    }

    private fun onCheckedChange(sched: Int, value: Int) {
        scope.launch {
            try {
                confRepository.conf("task$sched-on", value)
            } catch (_: Exception) {}
        }
    }

}