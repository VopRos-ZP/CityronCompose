package ru.cityron.domain.usecase.all.state

import ru.cityron.data.room.all.state.M3StateDatabase
import ru.cityron.data.room.all.state.toDto
import ru.cityron.domain.model.m3.M3State
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpsertM3StateUseCase @Inject constructor(
    private val m3StateDatabase: M3StateDatabase
) {

    suspend operator fun invoke(state: M3State) {
        m3StateDatabase.dao.upsertM3State(state.toDto())
    }

}