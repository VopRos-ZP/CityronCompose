package ru.cityron.presentation.screens.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.cityron.data.local.EventsStore
import ru.cityron.domain.model.EventWithDate
import ru.cityron.domain.repository.EventsRepository
import ru.cityron.domain.utils.Filters
import javax.inject.Inject
import kotlin.math.pow

@HiltViewModel
class EventsScreenViewModel @Inject constructor(
    private val eventsStore: EventsStore,
    private val eventsRepository: EventsRepository
) : ViewModel() {

    private val _events = MutableStateFlow<List<EventWithDate>?>(null)
    val events = _events.asStateFlow()

    /** filters **/
    private val _countOld = MutableStateFlow(1)
    private val _count = MutableStateFlow(_countOld.value)
    val count = _count.asStateFlow()

    private val _typesOld = MutableStateFlow(0)
    private val _types = MutableStateFlow(_typesOld.value)
    val types = _types.asStateFlow()

    private val _sourcesOld = MutableStateFlow(0)
    private val _sources = MutableStateFlow(_sourcesOld.value)
    val sources = _sources.asStateFlow()

    private val _reasonsOld = MutableStateFlow(0)
    private val _reasons = MutableStateFlow(_reasonsOld.value)
    val reasons = _reasons.asStateFlow()

    private val _isChanged = MutableStateFlow(false)
    val isChanged = _isChanged

    private val _isFiltered = MutableStateFlow(false)
    val isFiltered = _isFiltered.asStateFlow()

    fun setCount(count: Int) {
        _count.value = count
        setupIsChanged()
    }

    fun setTypes(types: Int) {
        _types.value = types
        setupIsChanged()
    }

    fun setSources(sources: Int) {
        _sources.value = sources
        setupIsChanged()
    }

    fun setReasons(reasons: Int) {
        _reasons.value = reasons
        setupIsChanged()
    }

    private fun setupIsChanged() {
        _isChanged.value =
            checkValuesWithOld(count.value, types.value, sources.value, reasons.value)
    }

    private fun setupIsFiltered() {
        _isFiltered.value =
            checkValuesWithOld(Filters.COUNT, Filters.TYPES, Filters.SOURCES, Filters.REASONS)
    }

    private fun checkValuesWithOld(count: Int, types: Int, sources: Int, reasons: Int): Boolean =
        _countOld.value != count
                || _typesOld.value != types
                || _sourcesOld.value != sources
                || _reasonsOld.value != reasons

    fun onSaveClick() {
        viewModelScope.launch {
            eventsStore.setCount(count.value)
            eventsStore.setTypes(types.value)
            eventsStore.setSources(sources.value)
            eventsStore.setReasons(reasons.value)
            delay(500)
            setupIsFiltered()
            setupIsChanged()
        }
    }

    fun fetchEventsFromStore() {
        viewModelScope.launch {
            launch { _events.value = null }
            launch {
                launch { eventsStore.count.collect { _countOld.emit(it) } }
                launch { eventsStore.types.collect { _typesOld.emit(it) } }
                launch { eventsStore.sources.collect { _sourcesOld.emit(it) } }
                launch { eventsStore.reasons.collect { _reasonsOld.emit(it) } }
            }
            launch {
                launch { _countOld.collect { _count.emit(it) } }
                launch { _typesOld.collect { _types.emit(it) } }
                launch { _sourcesOld.collect { _sources.emit(it) } }
                launch { _reasonsOld.collect { _reasons.emit(it) } }
            }
            delay(500)
            setupIsFiltered()
        }
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

    private fun indexToValue(i: Int): Int = when (i) {
        0 -> -1
        else -> 2f.pow(i - 1).toInt()
    }

}