package ru.cityron.domain.usecase.algo.fan

import ru.cityron.data.room.all.algo.AlgoDatabase
import ru.cityron.data.room.all.algo.fan.AlgoFanDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpsertAlgoFanUseCase @Inject constructor(
    private val algoDatabase: AlgoDatabase,
) {

    suspend operator fun invoke(dto: AlgoFanDto) {
        algoDatabase.fanDao.upsert(dto)
    }

}