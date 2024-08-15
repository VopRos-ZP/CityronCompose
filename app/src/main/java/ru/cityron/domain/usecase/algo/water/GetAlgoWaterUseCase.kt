package ru.cityron.domain.usecase.algo.water

import ru.cityron.data.room.all.algo.AlgoDatabase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAlgoWaterUseCase @Inject constructor(
    private val algoDatabase: AlgoDatabase,
) {

    suspend operator fun invoke() = algoDatabase.waterDao.fetch()

}