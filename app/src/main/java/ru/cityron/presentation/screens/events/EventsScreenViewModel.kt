package ru.cityron.presentation.screens.events

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.cityron.domain.model.EventWithDate
import javax.inject.Inject

@HiltViewModel
class EventsScreenViewModel @Inject constructor() : ViewModel() {

    private val _events = MutableStateFlow(listOf<EventWithDate>())
    val events = _events.asStateFlow()

    fun fetchEvents() {

    }

}