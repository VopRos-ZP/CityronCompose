package ru.cityron.domain.usecase.bind

import ru.cityron.domain.repository.BindRepository
import ru.cityron.domain.usecase.device.GetDeviceUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BindDeleteUseCase @Inject constructor(
    private val bindRepository: BindRepository,
    private val getDeviceUseCase: GetDeviceUseCase
) {

    suspend operator fun invoke(num: String): Boolean {
        return bindRepository.delete(num =  num, appUuid = getDeviceUseCase().deviceId)
    }

}