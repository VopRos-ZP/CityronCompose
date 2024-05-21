package ru.cityron.presentation.screens.editScheduler

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.cityron.domain.model.m3.M3Task
import ru.cityron.domain.repository.M3Repository
import javax.inject.Inject

@HiltViewModel
class EditSchedulerViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val m3Repository: M3Repository
) : ViewModel() {

    private val id: Int = checkNotNull(savedStateHandle["id"])

    private val _task = MutableStateFlow(M3Task(i = id))
    val task = _task.asStateFlow()

    fun conf() {

    }

}