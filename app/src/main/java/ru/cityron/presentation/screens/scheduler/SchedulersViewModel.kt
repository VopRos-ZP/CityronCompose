package ru.cityron.presentation.screens.scheduler

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.cityron.domain.repository.ConfRepository
import ru.cityron.domain.repository.M3Repository
import ru.cityron.presentation.mvi.BaseSharedViewModel
import javax.inject.Inject

@HiltViewModel
class SchedulersViewModel @Inject constructor(
    private val confRepository: ConfRepository,
    private val m3Repository: M3Repository,
) : BaseSharedViewModel<SchedulersViewState, Any, SchedulersViewIntent>(
    initialState = SchedulersViewState()
) {

    init {
        withViewModelScope {
            m3Repository.all.collect { all ->
                viewState = viewState.copy(
                    tasks = listOf(
                        all.sched.task0, all.sched.task1, all.sched.task2, all.sched.task3,
                        all.sched.task4, all.sched.task5, all.sched.task6, all.sched.task7,
                        all.sched.task8, all.sched.task9
                    ).mapIndexed { i, t -> t.copy(i = i) }
                )
            }
        }
    }

    override fun intent(viewEvent: SchedulersViewIntent) {
        when (viewEvent) {
            is SchedulersViewIntent.OnCheckedChange -> onCheckedChange(viewEvent.sched, viewEvent.value)
        }
    }

    private fun onCheckedChange(sched: Int, value: Int) {
        withViewModelScope {
            try {
                confRepository.conf("task$sched-on", value)
            } catch (_: Exception) {}
        }
    }

}