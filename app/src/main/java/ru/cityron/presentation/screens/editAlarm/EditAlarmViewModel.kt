package ru.cityron.presentation.screens.editAlarm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.cityron.domain.model.Alarm
import ru.cityron.domain.repository.ConfRepository
import ru.cityron.domain.usecase.GetM3SettingsUseCase
import javax.inject.Inject

@HiltViewModel
class EditAlarmViewModel @Inject constructor(
    private val confRepository: ConfRepository,
    private val getM3SettingsUseCase: GetM3SettingsUseCase
) : ViewModel() {

    private val _alarm = MutableStateFlow<Alarm?>(null)
    private val _localAlarm = MutableStateFlow<Alarm?>(null)
    val alarm =  _localAlarm.asStateFlow()

    private val _isChanged = MutableStateFlow(false)
    val isChanged = _isChanged.asStateFlow()

    fun fetchAlarm(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            launch { alarm.collect { _isChanged.emit(_alarm.value != it) } }

            val alarms = getM3SettingsUseCase().let {
                listOf(
                    it.alarm1, it.alarm2, it.alarm3, it.alarm4, it.alarm5,
                    it.alarm6, it.alarm7, it.alarm8, it.alarm9
                ).mapIndexed { i, alarm -> alarm.copy(i = i + 1) }
            }
            val alarm = alarms.getOrNull(id - 1)

            _alarm.emit(alarm)
            _localAlarm.emit(alarm)
        }
    }

    fun onActionChanged(action: Int) {
        _localAlarm.value = alarm.value?.copy(action = action)
    }

    fun onDelayChanged(delay: Int) {
        _localAlarm.value = alarm.value?.copy(delay = delay)
    }

    fun onValueChanged(value: Int) {
        _localAlarm.value = alarm.value?.copy(value = value)
    }

    fun onSaveClick() {
        viewModelScope.launch(Dispatchers.IO) {

        }
    }

}