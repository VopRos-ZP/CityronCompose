package ru.cityron.presentation.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.cityron.domain.repository.ConfRepository

abstract class ConfViewModel(
    private val confRepository: ConfRepository
) : ViewModel() {
    fun conf(key: String, value: Any) {
        viewModelScope.launch(Dispatchers.IO) {
            confRepository.conf(key, value)
        }
    }

}