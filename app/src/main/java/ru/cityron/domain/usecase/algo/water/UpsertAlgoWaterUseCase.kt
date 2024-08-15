package ru.cityron.domain.usecase.algo.water

import ru.cityron.data.room.all.algo.AlgoDatabase
import ru.cityron.data.room.all.algo.water.AlgoWaterDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpsertAlgoWaterUseCase @Inject constructor(
    private val algoDatabase: AlgoDatabase,
) {

    suspend operator fun invoke(dto: AlgoWaterDto) {
        algoDatabase.waterDao.upsert(dto)
    }

}