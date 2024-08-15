package ru.cityron.domain.usecase.algo.other

import ru.cityron.data.room.all.algo.AlgoDatabase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAlgoOtherUseCase @Inject constructor(
    private val algoDatabase: AlgoDatabase,
) {

    suspend operator fun invoke() = algoDatabase.otherDao.fetch()

}