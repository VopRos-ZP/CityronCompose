package ru.cityron.domain.usecase.bind

import ru.cityron.data.model.BindsAddResponse
import ru.cityron.domain.repository.BindRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BindConfirmUseCase @Inject constructor(
    private val bindRepository: BindRepository
) {

    suspend operator fun invoke(code: String): BindsAddResponse = bindRepository.confirm(code)

}