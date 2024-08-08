package ru.cityron.presentation.mvi

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlin.coroutines.CoroutineContext

abstract class KViewModel : ViewModel() {

    private val mainCoroutineExceptionHandler: CoroutineExceptionHandler? by lazy(LazyThreadSafetyMode.NONE) {
        getCoroutineExceptionHandler()
    }
    private val coroutineTags = hashMapOf<String, CoroutineScope>()
    private val mainCoroutineContext = (SupervisorJob() + Dispatchers.Main.immediate).run {
        val exceptionHandler = mainCoroutineExceptionHandler ?: return@run this
        this + exceptionHandler
    }

    val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    protected open fun getCoroutineExceptionHandler(): CoroutineExceptionHandler = CoroutineExceptionHandler { _, thr ->  }

    fun clear() {
        coroutineTags.forEach { it.value.cancel() }
        onCleared()
    }

}