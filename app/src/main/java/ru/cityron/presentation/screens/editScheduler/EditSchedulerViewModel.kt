package ru.cityron.presentation.screens.editScheduler

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.cityron.domain.model.m3.M3Task
import ru.cityron.domain.repository.ConfRepository
import ru.cityron.domain.repository.M3Repository
import ru.cityron.domain.usecase.GetM3SchedUseCase
import javax.inject.Inject

@HiltViewModel
class EditSchedulerViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getM3SchedUseCase: GetM3SchedUseCase,
    private val confRepository: ConfRepository
) : ViewModel() {

    private val id: Int = checkNotNull(savedStateHandle["id"])

    private val _task = MutableStateFlow(M3Task(i = id))
    val task = _task.asStateFlow()

    private val _localTask = _task
    val localTask = _localTask.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val sched = getM3SchedUseCase()
            _task.value = when (id) {
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
        }
    }

    fun onSaveClick() {
        viewModelScope.launch(Dispatchers.IO) {


        }
    }

}