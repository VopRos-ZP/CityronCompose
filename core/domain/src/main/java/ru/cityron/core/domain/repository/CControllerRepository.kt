package ru.cityron.core.domain.repository

import ru.cityron.core.domain.model.Controller

/**
 * Repository for current `Controller` value
 * **/
interface CControllerRepository {
    var controller: Controller?
}