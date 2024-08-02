package ru.cityron.domain.usecase

import ru.cityron.domain.model.m3.M3All
import ru.cityron.domain.repository.NetworkRepository
import ru.cityron.domain.utils.Path
import ru.cityron.domain.utils.fromJson
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetM3AllUseCase @Inject constructor(
    private val networkRepository: NetworkRepository
) {

    suspend operator fun invoke(): M3All = fromJson<M3All>(networkRepository.get(Path.JSON_ALL))

}