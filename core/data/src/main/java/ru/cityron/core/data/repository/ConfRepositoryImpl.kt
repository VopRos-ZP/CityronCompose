package ru.cityron.core.data.repository

import ru.cityron.core.domain.repository.ConfRepository
import ru.cityron.core.domain.repository.NetworkRepository
import javax.inject.Inject

class ConfRepositoryImpl @Inject constructor(
    private val networkRepository: NetworkRepository
) : ConfRepository {

    override suspend fun conf(key: String, value: Any): Result<String> {
        return networkRepository.conf(key, value)
    }

}