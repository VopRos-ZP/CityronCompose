package ru.cityron.domain.usecase

import ru.cityron.domain.model.JsonSched
import ru.cityron.domain.model.m3.M3Sched
import ru.cityron.domain.repository.NetworkRepository
import ru.cityron.domain.utils.Path
import ru.cityron.domain.utils.fromJson
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetM3SchedUseCase @Inject constructor(
    private val networkRepository: NetworkRepository,
) {

    suspend operator fun invoke(): M3Sched {
        return fromJson<JsonSched<M3Sched>>(networkRepository.get(Path.JSON_SCHED)).sched
    }

}