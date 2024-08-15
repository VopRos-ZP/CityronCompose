package ru.cityron.data.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.cityron.domain.model.Controller
import ru.cityron.domain.repository.CurrentRepository
import javax.inject.Inject

class CurrentRepositoryImpl @Inject constructor() : CurrentRepository {

    private val _current = MutableStateFlow<Controller?>(null)

    override var current = _current.asStateFlow()

    override fun setCurrentController(current: Controller?) {
        _current.value = current
    }

}