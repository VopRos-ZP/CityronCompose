package ru.cityron.domain.usecase.algo.pi

import ru.cityron.data.room.all.algo.AlgoDatabase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAlgoPiUseCase @Inject constructor(
    private val algoDatabase: AlgoDatabase,
) {

    suspend operator fun invoke(id: Int) = algoDatabase.piDao.fetch(id)

}