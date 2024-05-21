package ru.cityron.presentation.screens.scheduler

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.cityron.domain.model.m3.M3Task
import ru.cityron.domain.repository.M3Repository
import javax.inject.Inject

@HiltViewModel
class SchedulersViewModel @Inject constructor(
    private val m3Repository: M3Repository
) : ViewModel() {

    private val _tasks = MutableStateFlow(emptyList<M3Task>())
    val tasks = _tasks.asStateFlow()

    fun fetchTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            m3Repository.sched.collect {
                _tasks.value = listOf(
                    it.task0, it.task1, it.task2, it.task3,
                    it.task4, it.task5, it.task6, it.task7,
                    it.task8, it.task9
                ).mapIndexed { i, t -> t.copy(i = i) }
            }
        }
    }

}