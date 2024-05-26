package ru.cityron.presentation.screens.editScheduler

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.cityron.domain.model.m3.M3Task
import ru.cityron.domain.repository.ConfRepository
import ru.cityron.domain.usecase.GetM3SchedUseCase
import javax.inject.Inject

@HiltViewModel
class EditSchedulerViewModel @Inject constructor(
    private val getM3SchedUseCase: GetM3SchedUseCase,
    private val confRepository: ConfRepository
) : ViewModel() {

    private val _task = MutableStateFlow(M3Task())
    private val _localTask = MutableStateFlow(M3Task())
    val localTask = _localTask.asStateFlow()

    private val _isChanged = MutableStateFlow(false)
    val isChanged = _isChanged.asStateFlow()

    fun fetchTask(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val sched = getM3SchedUseCase()
            val task = when (id) {
                0 -> sched.task0
                1 -> sched.task1
                2 -> sched.task2
                3 -> sched.task3
                4 -> sched.task4
                5 -> sched.task5
                6 -> sched.task6
                7 -> sched.task7
                8 -> sched.task8
                9 -> sched.task9
                else -> throw RuntimeException()
            }
            _task.value = task
            _localTask.value = task
            localTask.collect {
                _isChanged.value = _task.value != it
            }
        }
    }

    fun onHourChanged(hour: Int) {
        _localTask.value = localTask.value.copy(hour = hour)
    }

    fun onMinChanged(min: Int) {
        _localTask.value = localTask.value.copy(min = min)
    }

    fun onDayChanged(day: Int) {
        _localTask.value = localTask.value.copy(day = day)
    }

    fun onModeChanged(mode: Int) {
        _localTask.value = localTask.value.copy(mode = mode)
    }

    fun onFanChanged(fan: Int) {
        _localTask.value = localTask.value.copy(fan = fan)
    }

    fun onTempChanged(temp: Int) {
        _localTask.value = localTask.value.copy(temp = temp)
    }

    fun onPowerChanged(power: Int) {
        _localTask.value = localTask.value.copy(power = power)
    }

    fun onSaveClick() {
        viewModelScope.launch(Dispatchers.IO) {


        }
    }

}