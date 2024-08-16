package ru.cityron.domain.usecase.bind

import ru.cityron.domain.model.Controller
import ru.cityron.domain.repository.BindCurrentRepository
import ru.cityron.domain.repository.BindRepository
import ru.cityron.domain.usecase.device.GetDeviceUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BindDeleteUseCase @Inject constructor(
    private val bindRepository: BindRepository,
    private val getDeviceUseCase: GetDeviceUseCase,
    private val bindCurrentRepository: BindCurrentRepository,
) {

    suspend operator fun invoke(controller: Controller): Boolean {
        bindCurrentRepository.controller = controller
        return bindRepository.delete(num = controller.num, appUuid = getDeviceUseCase().deviceId)
    }

}