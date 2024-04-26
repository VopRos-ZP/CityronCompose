package ru.cityron.domain.repository

import ru.cityron.domain.model.Controller
import ru.cityron.domain.model.DataSource

interface CurrentRepository {
    var current: Pair<Controller, DataSource>?
}