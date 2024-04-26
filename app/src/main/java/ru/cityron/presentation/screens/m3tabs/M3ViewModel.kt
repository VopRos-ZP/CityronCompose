package ru.cityron.presentation.screens.m3tabs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import ru.cityron.domain.model.m3.M3State
import ru.cityron.domain.repository.CurrentRepository
import ru.cityron.domain.repository.M3Repository
import javax.inject.Inject

@HiltViewModel
class M3ViewModel @Inject constructor(
    m3Repository: M3Repository
) : ViewModel() {

    private val _state = MutableStateFlow(M3State())
    val state = m3Repository.state.stateIn(
        scope = viewModelScope.plus(Dispatchers.IO),
        started = SharingStarted.Lazily,
        initialValue = M3State()
    )

    fun fetchState() {
        viewModelScope.launch {

        }
    }

}