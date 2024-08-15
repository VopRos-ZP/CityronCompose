package ru.cityron.domain.usecase.algo.electric

import ru.cityron.data.room.all.algo.AlgoDatabase
import ru.cityron.data.room.all.algo.electric.AlgoElectricDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpsertAlgoElectricUseCase @Inject constructor(
    private val algoDatabase: AlgoDatabase,
) {

    suspend operator fun invoke(dto: AlgoElectricDto) {
        algoDatabase.electricDao.upsert(dto)
    }

}