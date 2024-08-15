package ru.cityron.data.repository

import ru.cityron.domain.model.Controller
import ru.cityron.domain.repository.BindCurrentRepository
import javax.inject.Inject

class BindCurrentRepositoryImpl @Inject constructor() : BindCurrentRepository {
    override lateinit var controller: Controller
}