package ru.cityron.presentation.screens.editAlarm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.cityron.domain.repository.ConfRepository
import ru.cityron.domain.repository.M3Repository
import ru.cityron.domain.usecase.GetM3SettingsUseCase
import javax.inject.Inject

@HiltViewModel
class EditAlarmViewModel @Inject constructor(
    private val confRepository: ConfRepository,
    private val getM3SettingsUseCase: GetM3SettingsUseCase,
    private val m3Repository: M3Repository,
) : ViewModel() {

    private val _state = MutableStateFlow(EditAlarmViewState())
    val state = _state.asStateFlow()

    fun fetchAlarm(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            launch {
                m3Repository.static.collect { static ->
                    val minAlarms = static.settingsMin.let {
                        listOf(
                            it.alarm1, it.alarm2, it.alarm3, it.alarm4, it.alarm5,
                            it.alarm6, it.alarm7, it.alarm8, it.alarm9
                        ).mapIndexed { i, alarm -> alarm.copy(i = i + 1) }
                    }
                    val maxAlarms = static.settingsMax.let {
                        listOf(
                            it.alarm1, it.alarm2, it.alarm3, it.alarm4, it.alarm5,
                            it.alarm6, it.alarm7, it.alarm8, it.alarm9
                        ).mapIndexed { i, alarm -> alarm.copy(i = i + 1) }
                    }
                    val minAlarm = minAlarms[id - 1]
                    val maxAlarm = maxAlarms[id - 1]

                    _state.emit(
                        state.value.copy(
                            delayValues = (minAlarm.delay..maxAlarm.delay).toList(),
                            valueValues = (minAlarm.value..maxAlarm.value).toList()
                        )
                    )
                }
            }

            val alarms = getM3SettingsUseCase().let {
                listOf(
                    it.alarm1, it.alarm2, it.alarm3, it.alarm4, it.alarm5,
                    it.alarm6, it.alarm7, it.alarm8, it.alarm9
                ).mapIndexed { i, alarm -> alarm.copy(i = i + 1) }
            }
            val alarm = alarms.getOrNull(id - 1)

            _state.value = state.value.copy(alarm = alarm, localAlarm = alarm)
        }
    }

    private fun updateIsChanged() {
        val value = state.value
        _state.value = value.copy(isChanged = value.alarm != value.localAlarm)
    }

    fun onActionChanged(action: Int) {
        _state.value = state.value.copy(localAlarm = state.value.localAlarm?.copy(action = action))
        updateIsChanged()
    }

    fun onDelayChanged(delay: Int) {
        _state.value = state.value.copy(localAlarm = state.value.localAlarm?.copy(delay = delay))
        updateIsChanged()
    }

    fun onValueChanged(value: Int) {
        _state.value = state.value.copy(localAlarm = state.value.localAlarm?.copy(value = value))
        updateIsChanged()
    }

    fun onSaveClick() {
        viewModelScope.launch(Dispatchers.IO) {

        }
    }

}