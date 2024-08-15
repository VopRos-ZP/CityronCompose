package ru.cityron.domain.usecase.algo.timing

import ru.cityron.data.room.all.algo.AlgoDatabase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAlgoTimingUseCase @Inject constructor(
    private val algoDatabase: AlgoDatabase,
) {

    suspend operator fun invoke() = algoDatabase.timingDao.fetch()

}