package ru.cityron.domain.usecase.algo.fan

import ru.cityron.data.room.all.algo.AlgoDatabase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAlgoFanUseCase @Inject constructor(
    private val algoDatabase: AlgoDatabase,
) {

    suspend operator fun invoke(id: Int) = algoDatabase.fanDao.fetch(id)

}