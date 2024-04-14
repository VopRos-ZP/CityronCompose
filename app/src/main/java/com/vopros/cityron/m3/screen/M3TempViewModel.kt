package com.vopros.cityron.m3.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.vopros.cityron.ui.ConfViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.cityron.core.domain.repository.ConfRepository
import javax.inject.Inject

@HiltViewModel
class M3TempViewModel @Inject constructor(
    confRepository: ConfRepository,
) : ConfViewModel(confRepository) {

    var temp by mutableIntStateOf(220)
        private set

    private var isFetching = false

    fun fetchTemp() {
        isFetching = true
        viewModelScope.launch {
            while (isFetching) {
//                m3ControllerRepository.fetchAll().onSuccess {
//                    temp = it.state.set.temp
//                }
                delay(1000)
            }
        }
    }

    fun dispose() {
        isFetching = false
    }

}