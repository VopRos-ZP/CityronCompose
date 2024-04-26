package ru.cityron.data.repository

import ru.cityron.domain.model.Controller
import ru.cityron.domain.model.DataSource
import ru.cityron.domain.repository.CurrentRepository
import javax.inject.Inject

class CurrentRepositoryImpl @Inject constructor() : CurrentRepository {

    override var current: Pair<Controller, DataSource>? = null

}