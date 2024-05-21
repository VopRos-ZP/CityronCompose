package ru.cityron.presentation.screens.scheduler

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.cityron.domain.model.m3.M3Task
import ru.cityron.domain.usecase.GetM3SchedUseCase
import javax.inject.Inject

@HiltViewModel
class SchedulersViewModel @Inject constructor(
    private val getM3SchedUseCase: GetM3SchedUseCase
) : ViewModel() {

    private val _isRefresh = MutableStateFlow(false)
    val isRefresh = _isRefresh.asStateFlow()

    private val _tasks = MutableStateFlow(emptyList<M3Task>())
    val tasks = _tasks.asStateFlow()

    fun refresh() {
        viewModelScope.launch(Dispatchers.IO) {
            _isRefresh.value = true
            delay(500)
            _tasks.value = getM3SchedUseCase().let {
                listOf(
                    it.task0, it.task1, it.task2, it.task3,
                    it.task4, it.task5, it.task6, it.task7,
                    it.task8, it.task9
                ).mapIndexed { i, t -> t.copy(i = i) }
            }
            _isRefresh.value = false
        }
    }

}