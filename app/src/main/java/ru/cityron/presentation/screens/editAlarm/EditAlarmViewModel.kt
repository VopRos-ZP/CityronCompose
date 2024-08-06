package ru.cityron.presentation.screens.editAlarm

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.cityron.R
import ru.cityron.domain.repository.ConfRepository
import ru.cityron.domain.usecase.GetM3AllUseCase
import ru.cityron.presentation.mvi.MviViewModel
import ru.cityron.presentation.mvi.SnackbarResult
import javax.inject.Inject

@HiltViewModel
class EditAlarmViewModel @Inject constructor(
    private val confRepository: ConfRepository,
    private val getM3AllUseCase: GetM3AllUseCase,
) : MviViewModel<EditAlarmViewState, EditAlarmViewIntent>() {

    override fun intent(intent: EditAlarmViewIntent) {
        when (intent) {
            is EditAlarmViewIntent.Launch -> launch(intent.id)
            is EditAlarmViewIntent.OnSaveClick ->  onSaveClick()
            is EditAlarmViewIntent.OnActionChange -> updateState {
                copy(
                    action = intent.value,
                    isChanged = intent.value != actionOld || delay != delayOld || value != valueOld
                )
            }
            is EditAlarmViewIntent.OnDelayChange -> updateState {
                copy(
                    delay = intent.value,
                    isChanged = intent.value != delayOld || action != actionOld || value != valueOld
                )
            }
            is EditAlarmViewIntent.OnValueChange -> updateState {
                copy(
                    value = intent.value,
                    isChanged = intent.value != valueOld || delay != delayOld || action != actionOld
                )
            }
            is EditAlarmViewIntent.OnSnakbarResultChange -> updateState {
                copy(result = intent.value)
            }
        }
    }

    private fun launch(id: Int) {
        if (state.value == null) {
            updateState({ copy() }, EditAlarmViewState(id))
        }
        scope.launch {
            val all = getM3AllUseCase()

            val minAlarms = all.static.settingsMin.let {
                listOf(
                    it.alarm1, it.alarm2, it.alarm3, it.alarm4, it.alarm5,
                    it.alarm6, it.alarm7, it.alarm8, it.alarm9
                ).mapIndexed { i, alarm -> alarm.copy(i = i + 1) }
            }
            val maxAlarms = all.static.settingsMax.let {
                listOf(
                    it.alarm1, it.alarm2, it.alarm3, it.alarm4, it.alarm5,
                    it.alarm6, it.alarm7, it.alarm8, it.alarm9
                ).mapIndexed { i, alarm -> alarm.copy(i = i + 1) }
            }
            val minAlarm = minAlarms[id - 1]
            val maxAlarm = maxAlarms[id - 1]

            val alarms = all.settings.let {
                listOf(
                    it.alarm1, it.alarm2, it.alarm3, it.alarm4, it.alarm5,
                    it.alarm6, it.alarm7, it.alarm8, it.alarm9
                ).mapIndexed { i, alarm -> alarm.copy(i = i + 1) }
            }
            val alarm = alarms[id - 1]

            updateState {
                copy(
                    actionOld = alarm.action,
                    action = alarm.action,

                    delayOld = alarm.delay,
                    delay = alarm.delay,

                    valueOld = alarm.value,
                    value = alarm.value,

                    delayValues = (minAlarm.delay..maxAlarm.delay).toList(),
                    valueValues = (minAlarm.value..maxAlarm.value).toList()
                )
            }
        }
    }

    private fun onSaveClick() {
        scope.launch {
            state.value?.let {
                try {
                    confRepository.conf("alarm${it.i}-delay", "${it.delay}\n")
                    confRepository.conf("alarm${it.i}-value", "${it.value}\n")
                    confRepository.conf("alarm${it.i}-action", "${it.action}\n")
                    updateState {
                        copy(
                            isChanged = false,
                            result = SnackbarResult(R.string.success_save_settings, false)
                        )
                    }
                } catch (_: Exception) {
                    updateState {
                        copy(
                            isChanged = true,
                            result = SnackbarResult(R.string.error_save_settings, true)
                        )
                    }
                }
            }
        }
    }

}