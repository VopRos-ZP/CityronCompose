package ru.cityron.presentation.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.plus

abstract class MviViewModel <S, I> : ViewModel() {

    private val _state = MutableStateFlow<S?>(null)
    val state = _state.asStateFlow()

    protected fun updateState(onUpdate: S.() -> S) {
        updateState(onUpdate = onUpdate, initial = null)
    }

    protected fun updateState(onUpdate: S.() -> S, initial: S? =  null) {
        _state.value = initial ?: state.value?.onUpdate()
    }

    abstract fun intent(intent: I)

    protected val scope = viewModelScope.plus(Dispatchers.IO)

}