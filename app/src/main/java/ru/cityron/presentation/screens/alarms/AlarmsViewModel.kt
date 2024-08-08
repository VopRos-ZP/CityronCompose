package ru.cityron.presentation.screens.alarms

import dagger.hilt.android.lifecycle.HiltViewModel
import ru.cityron.domain.model.Alarm
import ru.cityron.domain.repository.ConfRepository
import ru.cityron.domain.repository.M3Repository
import ru.cityron.domain.utils.toInt
import ru.cityron.presentation.mvi.BaseSharedViewModel
import javax.inject.Inject

@HiltViewModel
class AlarmsViewModel @Inject constructor(
    private val confRepository: ConfRepository,
    private val m3Repository: M3Repository
) : BaseSharedViewModel<AlarmsViewState, AlarmsViewAction, AlarmsViewIntent>(
    initialState = AlarmsViewState()
) {

    init {
        withViewModelScope {
            m3Repository.all.collect { all ->
                val s = all.settings
                val alarms = listOf(
                    s.alarm1, s.alarm2, s.alarm3, s.alarm4, s.alarm5,
                    s.alarm6, s.alarm7, s.alarm8, s.alarm9
                ).mapIndexed { i, alarm -> alarm.copy(i = i + 1) }

                viewState = viewState.copy(alarms = alarms)
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