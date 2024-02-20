package com.vopros.cityron.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vopros.cityron.controller.ControllerItem
import com.vopros.cityron.local.LocalStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val localStore: LocalStore
) : ViewModel() {

    private val _controllers = MutableStateFlow<List<ControllerItem>?>(null)
    val controllers = _controllers.asStateFlow()

    fun fetchControllers() {
        viewModelScope.launch {
            localStore.data.collect {
                _controllers.emit(it.tokens.map { t -> t.controller })
            }
        }
    }

}