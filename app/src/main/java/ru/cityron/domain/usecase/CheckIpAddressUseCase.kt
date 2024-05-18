package ru.cityron.domain.usecase

import ru.cityron.domain.repository.CheckIpRepository
import javax.inject.Inject

class CheckIpAddressUseCase @Inject constructor(
    private val checkIpRepository: CheckIpRepository
) {

    suspend operator fun invoke(ip: String): Boolean {
        return checkIpRepository.checkIpAddress(ip)
    }

}