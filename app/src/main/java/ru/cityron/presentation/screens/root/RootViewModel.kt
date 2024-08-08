package ru.cityron.presentation.screens.root

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.cityron.data.room.event.EventDatabase
import ru.cityron.data.room.event.EventDto
import ru.cityron.domain.model.Controller
import ru.cityron.domain.model.DataSource
import ru.cityron.domain.repository.ConnectivityRepository
import ru.cityron.domain.repository.CurrentRepository
import ru.cityron.domain.utils.Filters
import ru.cityron.presentation.mvi.BaseSharedViewModel
import javax.inject.Inject

@HiltViewModel
class RootViewModel @Inject constructor(
    private val connectivityRepository: ConnectivityRepository,
    private val currentRepository: CurrentRepository,
    private val eventDatabase: EventDatabase,
) : BaseSharedViewModel<RootViewState, Any, RootViewIntent>(
    initialState = RootViewState()
) {

    init {
        withViewModelScope {
            launch {
                if (eventDatabase.dao.fetchAll().isEmpty()) {
                    eventDatabase.dao.upsert(EventDto(
                        id = 0,
                        count = Filters.COUNT,
                        types = Filters.TYPES,
                        reasons = Filters.REASONS,
                        sources = Filters.SOURCES
                    ))
                }
            }
            launch {
                connectivityRepository.controllersDataSources.collect {
                    if (currentRepository.current != null) {
                        val oldController = currentRepository.current!!.first
                        val founded = it.keys.toList().find { c -> c.idCpu == oldController.idCpu }
                        if (oldController != founded) {
                            currentRepository.current = founded!! to it[founded]!!
                        }
                    }
                    viewState = viewState.copy(controllers = it)
                }
            }
        }
    }

    override fun intent(viewEvent: RootViewIntent) {
        when (viewEvent) {
            is RootViewIntent.OnSelectController -> onSelectController(viewEvent.value)
        }
    }

    private fun onSelectController(pair: Pair<Controller, DataSource>) {
        currentRepository.current = pair
    }

}