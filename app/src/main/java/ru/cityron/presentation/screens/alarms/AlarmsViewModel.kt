package ru.cityron.presentation.screens.alarms

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.cityron.domain.model.Alarm
import ru.cityron.domain.repository.ConfRepository
import ru.cityron.domain.usecase.all.alarms.GetM3AlarmsUseCase
import ru.cityron.domain.utils.toInt
import ru.cityron.presentation.mvi.BaseSharedViewModel
import javax.inject.Inject

@HiltViewModel
class AlarmsViewModel @Inject constructor(
    private val confRepository: ConfRepository,
    private val getM3AlarmsUseCase: GetM3AlarmsUseCase,
) : BaseSharedViewModel<AlarmsViewState, AlarmsViewAction, AlarmsViewIntent>(
    initialState = AlarmsViewState()
) {

    init {
        withViewModelScope {
            getM3AlarmsUseCase.flow.collect {
                viewState = viewState.copy(alarms = it)
            }
        }
    }

    override fun intent(viewEvent: AlarmsViewIntent) {
        when (viewEvent) {
            is AlarmsViewIntent.OnAlarmEnChange -> onAlarmEnChange(viewEvent.alarm, viewEvent.value)
        }
    }

    private fun onAlarmEnChange(alarm: Alarm, en: Boolean) {
        withViewModelScope {
            confRepository.conf("alarm${alarm.i}-en", en.toInt())
        }
    }

}