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
        return try {
            val controller = Controller(
                id = 0,
                name = "",
                ipAddress = ip,
                idCpu = "",
                idUsr = "",
                status = Status.Online(DataSource.LOCAL),
                num = "00"
            )
            currentRepository.setCurrentController(controller)
            networkRepository.get(JSON_STATE).contains("error").not()
        } catch (_: Exception) {
            false
        }
    }

}