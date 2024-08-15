package ru.cityron.domain.usecase.algo.timing

import ru.cityron.data.room.all.algo.AlgoDatabase
import ru.cityron.data.room.all.algo.timings.AlgoTimingDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpsertAlgoTimingUseCase @Inject constructor(
    private val algoDatabase: AlgoDatabase,
) {

    suspend operator fun invoke(dto: AlgoTimingDto) {
        algoDatabase.timingDao.upsert(dto)
    }

}