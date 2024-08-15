package ru.cityron.domain.repository

import ru.cityron.domain.model.Controller

interface BindCurrentRepository {
    var controller: Controller
}