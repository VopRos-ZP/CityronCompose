package ru.cityron.domain.usecase.bind

import ru.cityron.domain.repository.BindRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BindCancelUseCase @Inject constructor(
    private val bindRepository: BindRepository
) {

    suspend operator fun invoke() { bindRepository.cancel() }

}