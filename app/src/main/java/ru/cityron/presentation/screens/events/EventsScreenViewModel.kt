package ru.cityron.presentation.screens.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.cityron.domain.model.EventWithDate
import ru.cityron.domain.repository.EventsRepository
import javax.inject.Inject
import kotlin.math.pow

@HiltViewModel
class EventsScreenViewModel @Inject constructor(
    private val eventsRepository: EventsRepository
) : ViewModel() {

    private val _events = MutableStateFlow(listOf<EventWithDate>())
    val events = _events.asStateFlow()

    /** filters **/
    private val _countOld = MutableStateFlow(1)
    private val _count = _countOld
    val count = _count.asStateFlow()

    private val _typesOld = MutableStateFlow(0)
    private val _types = _typesOld
    val types = _types.asStateFlow()

    private val _sourcesOld = MutableStateFlow(0)
    private val _sources = _sourcesOld
    val sources = _sources.asStateFlow()

    private val _reasonsOld = MutableStateFlow(0)
    private val _reasons = _reasonsOld
    val reasons = _reasons.asStateFlow()

    private val _isChanged = MutableStateFlow(false)
    val isChanged = _isChanged

    fun setCount(count: Int) {
        _count.value = count
        _isChanged.value = _countOld.value != count
    }

    fun setTypes(types: Int) {
        _types.value = types
        _isChanged.value = _typesOld.value != types
    }

    fun setSources(sources: Int) {
        _sources.value = sources
        _isChanged.value = _sourcesOld.value != sources
    }

    fun setReasons(reasons: Int) {
        _reasons.value = reasons
        _isChanged.value = _reasonsOld.value != reasons
    }

    private fun indexToValue(i: Int): Int = when (i) {
        0 -> -1
        else -> 2f.pow(i - 1).toInt()
    }

    fun onSaveClick() {
        _countOld.value = count.value
        _typesOld.value = types.value
        _sourcesOld.value = sources.value
        _reasonsOld.value = reasons.value
    }

    fun fetchEvents() {
        viewModelScope.launch(Dispatchers.IO) {
            _events.value = eventsRepository.fetchEvents(
                count = when (_countOld.value) {
                    1 -> 50
                    2 -> 100
                    3 -> 500
                    4 -> 1000
                    5 -> 1500
                    else -> -1
                },
                types = indexToValue(_typesOld.value),
                sources = indexToValue(_sourcesOld.value),
                reasons = indexToValue(_reasonsOld.value)
            )
        }
    }

}