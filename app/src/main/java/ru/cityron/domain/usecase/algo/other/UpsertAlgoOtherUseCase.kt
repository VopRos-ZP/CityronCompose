package ru.cityron.domain.usecase.algo.other

import ru.cityron.data.room.all.algo.AlgoDatabase
import ru.cityron.data.room.all.algo.other.AlgoOtherDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpsertAlgoOtherUseCase @Inject constructor(
    private val algoDatabase: AlgoDatabase,
) {

    suspend operator fun invoke(dto: AlgoOtherDto) {
        algoDatabase.otherDao.upsert(dto)
    }

}