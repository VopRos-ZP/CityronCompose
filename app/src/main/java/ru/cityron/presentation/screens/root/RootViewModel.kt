package ru.cityron.presentation.screens.root

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.cityron.domain.model.Controller
import ru.cityron.domain.model.DataSource
import ru.cityron.domain.repository.ConnectivityRepository
import ru.cityron.domain.repository.CurrentRepository
import javax.inject.Inject

@HiltViewModel
class RootViewModel @Inject constructor(
    private val connectivityRepository: ConnectivityRepository,
    private val currentRepository: CurrentRepository,
) : ViewModel() {

    private val _controllers = MutableStateFlow(emptyMap<Controller, DataSource>())
    val controllers = _controllers.asStateFlow()

    fun fetchControllers() {
        viewModelScope.launch {
            connectivityRepository.controllersDataSources.collect {
                _controllers.emit(it)
            }
        }
    }

    fun selectController(pair: Pair<Controller, DataSource>) {
        currentRepository.current = pair
    }

}