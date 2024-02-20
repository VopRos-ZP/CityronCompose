package com.vopros.cityron.ui.screens.findController

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vopros.cityron.local.Info
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FindControllerViewModel @Inject constructor(

) : ViewModel() {

    private val _info = MutableStateFlow<List<Info>?>(null)
    val info = _info.asStateFlow()

    fun sendUDP() {
        viewModelScope.launch {

        }
    }

}