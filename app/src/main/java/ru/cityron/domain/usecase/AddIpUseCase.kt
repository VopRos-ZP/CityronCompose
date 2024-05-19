package ru.cityron.domain.usecase

import ru.cityron.domain.model.Ip
import ru.cityron.domain.repository.IpRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddIpUseCase @Inject constructor(
    private val ipRepository: IpRepository
) {

    suspend operator fun invoke(ip: String) {
        ipRepository.upsert(Ip(address = ip))
    }

}