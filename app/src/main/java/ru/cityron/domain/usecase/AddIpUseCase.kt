package ru.cityron.domain.usecase

import ru.cityron.domain.model.Ip
import ru.cityron.domain.repository.IpRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddIpUseCase @Inject constructor(
    private val ipRepository: IpRepository
) {

    suspend operator fun invoke(ip: String): Boolean {
        return when (ipRepository.fetchAll().find { it.address == ip }) {
            null -> {
                ipRepository.upsert(Ip(address = ip))
                false
            }
            else -> true
        }
    }

}