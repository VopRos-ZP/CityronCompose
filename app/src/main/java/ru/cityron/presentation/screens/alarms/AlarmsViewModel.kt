package ru.cityron.presentation.screens.alarms

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import ru.cityron.domain.model.Alarm
import ru.cityron.domain.repository.ConfRepository
import ru.cityron.domain.repository.M3Repository
import ru.cityron.presentation.components.ConfViewModel
import javax.inject.Inject

@HiltViewModel
class AlarmsViewModel @Inject constructor(
    confRepository: ConfRepository,
    private val m3Repository: M3Repository
) : ConfViewModel(confRepository) {

    private val _alarms = MutableStateFlow(emptyList<Alarm>())



}