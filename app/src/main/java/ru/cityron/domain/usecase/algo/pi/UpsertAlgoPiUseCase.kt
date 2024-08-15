package ru.cityron.domain.usecase.algo.pi

import ru.cityron.data.room.all.algo.AlgoDatabase
import ru.cityron.data.room.all.algo.pi.AlgoPiDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpsertAlgoPiUseCase @Inject constructor(
    private val algoDatabase: AlgoDatabase,
) {

    suspend operator fun invoke(dto: AlgoPiDto) {
        algoDatabase.piDao.upsert(dto)
    }

}