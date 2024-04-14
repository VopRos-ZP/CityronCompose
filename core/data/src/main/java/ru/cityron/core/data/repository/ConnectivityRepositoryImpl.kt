package ru.cityron.core.data.repository

import ru.cityron.core.domain.repository.ConnectivityRepository
import ru.cityron.core.domain.repository.NetworkRepository
import ru.cityron.core.domain.utils.Path
import javax.inject.Inject

class ConnectivityRepositoryImpl @Inject constructor(
    private val networkRepository: NetworkRepository
) : ConnectivityRepository {

    override suspend fun isConnected(): Boolean {
        return try {
            networkRepository
                .get(Path.JSON_STATE)
                .contains("html")
                .not() // if not contains html (Auth page) when controller connected
                       // edit soon when added server integration
        } catch (_: Exception) { false }
    }

}