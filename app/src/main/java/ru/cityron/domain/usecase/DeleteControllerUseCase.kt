package ru.cityron.domain.usecase

import ru.cityron.domain.repository.ControllerRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeleteControllerUseCase @Inject constructor(
    private val controllerRepository: ControllerRepository
) {

    suspend operator fun invoke(id: Int) {
        controllerRepository.delete(id)
    }

}