package ru.cityron.presentation.mvi

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class BaseSharedViewModel<State : Any, Action, Event>(initialState: State) : KViewModel() {

    private val _viewStates = MutableStateFlow(initialState)

    private val _viewActions = MutableSharedFlow<Action?>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    fun state(): WrappedStateFlow<State> = WrappedStateFlow(_viewStates.asStateFlow())

    fun action(): WrappedSharedFlow<Action?> = WrappedSharedFlow(_viewActions.asSharedFlow())

    protected var viewState: State
        get() = _viewStates.value
        set(value) { _viewStates.value = value }

    protected var viewAction: Action?
        get() = _viewActions.replayCache.last()
        set(value) {
            _viewActions.tryEmit(value)
        }

    abstract fun intent(viewEvent: Event)

    /**
     * Convenient method to perform work in [scope] scope.
     */
    protected fun withViewModelScope(scope: CoroutineScope = this.scope, block: suspend CoroutineScope.() -> Unit) {
        scope.launch(block = block)
    }

}