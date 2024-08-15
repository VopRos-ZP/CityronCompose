package ru.cityron.domain.usecase.bind

import ru.cityron.domain.repository.BindRepository
import ru.cityron.domain.usecase.device.GetDeviceUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BindAddUseCase @Inject constructor(
    private val bindRepository: BindRepository,
    private val getDeviceUseCase: GetDeviceUseCase,
) {

    suspend operator fun invoke(accessLevel: String): String {
        val dto = getDeviceUseCase()
        return bindRepository.add(
            accessLevel = accessLevel,
            appUuid = dto.deviceId,
            phoneInfo = dto.deviceName
        )
    }

}