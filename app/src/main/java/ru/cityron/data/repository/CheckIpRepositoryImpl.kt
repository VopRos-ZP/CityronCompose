package ru.cityron.data.repository

import ru.cityron.domain.model.Controller
import ru.cityron.domain.model.DataSource
import ru.cityron.domain.model.Status
import ru.cityron.domain.repository.CheckIpRepository
import ru.cityron.domain.repository.CurrentRepository
import ru.cityron.domain.repository.NetworkRepository
import ru.cityron.domain.utils.Path.JSON_STATE
import javax.inject.Inject

class CheckIpRepositoryImpl @Inject constructor(
    private val currentRepository: CurrentRepository,
    private val networkRepository: NetworkRepository
) : CheckIpRepository {

    override suspend fun checkIpAddress(ip: String): Boolean {
        try {
            currentRepository.current = Controller(
                id = 0,
                name = "",
                ipAddress = ip,
                idCpu = "",
                idUsr = ""
            ) to DataSource.Local(Status.ONLINE)
            val body = networkRepository.get(JSON_STATE)
            return body.contains("error").not()
        } catch (_: Exception) {
            return false
        }
    }

}