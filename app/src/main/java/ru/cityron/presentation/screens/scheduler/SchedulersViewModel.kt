package ru.cityron.presentation.screens.scheduler

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.cityron.domain.repository.ConfRepository
import ru.cityron.domain.usecase.all.sched.GetM3TasksUseCase
import ru.cityron.presentation.mvi.BaseSharedViewModel
import javax.inject.Inject

@HiltViewModel
class SchedulersViewModel @Inject constructor(
    private val confRepository: ConfRepository,
    private val getM3TasksUseCase: GetM3TasksUseCase,
) : BaseSharedViewModel<SchedulersViewState, Any, SchedulersViewIntent>(
    initialState = SchedulersViewState()
) {

    init {
        withViewModelScope {
            getM3TasksUseCase.flow.collect {
                viewState = viewState.copy(tasks = it)
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