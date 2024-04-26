package ru.cityron.domain.usecase

import ru.cityron.domain.model.Controller
import ru.cityron.domain.repository.ControllerRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpsertControllerUseCase @Inject constructor(
    private val controllerRepository: ControllerRepository
) {

    suspend operator fun invoke(controller: Controller) {
        controllerRepository.upsert(controller)
    }

}