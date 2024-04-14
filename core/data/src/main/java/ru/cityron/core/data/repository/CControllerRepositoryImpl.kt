package ru.cityron.core.data.repository

import ru.cityron.core.domain.model.Controller
import ru.cityron.core.domain.repository.CControllerRepository
import javax.inject.Inject

class CControllerRepositoryImpl @Inject constructor() : CControllerRepository {
    override var controller: Controller? = null
}