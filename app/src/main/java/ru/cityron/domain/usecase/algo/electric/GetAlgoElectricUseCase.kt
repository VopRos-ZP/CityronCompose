package ru.cityron.domain.usecase.algo.electric

import ru.cityron.data.room.all.algo.AlgoDatabase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAlgoElectricUseCase @Inject constructor(
    private val algoDatabase: AlgoDatabase,
) {

    suspend operator fun invoke() = algoDatabase.electricDao.fetch()

}