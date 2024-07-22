package ru.cityron.presentation.screens.alarms

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.cityron.domain.model.Alarm
import ru.cityron.domain.repository.ConfRepository
import ru.cityron.domain.repository.M3Repository
import ru.cityron.domain.utils.toInt
import ru.cityron.presentation.components.ConfViewModel
import javax.inject.Inject

@HiltViewModel
class AlarmsViewModel @Inject constructor(
    private val confRepository: ConfRepository,
    private val m3Repository: M3Repository
) : ConfViewModel(confRepository) {

    private val _alarms = MutableStateFlow(emptyList<Alarm>())
    val alarms = _alarms.asStateFlow()

    fun fetchAlarms() {
        viewModelScope.launch(Dispatchers.IO) {
            m3Repository.settings.collect {  s ->
                val alarms = listOf(
                    s.alarm1, s.alarm2, s.alarm3, s.alarm4, s.alarm5,
                    s.alarm6, s.alarm7, s.alarm8, s.alarm9
                ).mapIndexed { i, alarm -> alarm.copy(i = i + 1) }
                _alarms.emit(alarms)
            }
        }
    }

    fun setEnAlarm(alarm: Alarm, en: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            confRepository.conf("alarm${alarm.i}-en", en.toInt())
        }
    }

}