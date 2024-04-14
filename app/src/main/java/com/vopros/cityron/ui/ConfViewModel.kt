package com.vopros.cityron.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.cityron.core.domain.repository.ConfRepository

abstract class ConfViewModel(
    private val confRepository: ConfRepository
) : ViewModel() {

    fun conf(key: String, value: Any) {
        viewModelScope.launch(Dispatchers.Main) {
            confRepository.conf(key, value)
        }
    }

}