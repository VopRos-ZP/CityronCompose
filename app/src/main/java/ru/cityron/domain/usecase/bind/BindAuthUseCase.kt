package ru.cityron.domain.usecase.bind

import ru.cityron.domain.repository.BindRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BindAuthUseCase @Inject constructor(
    private val bindRepository: BindRepository
) {

    suspend fun auth(pass: String, level: Char): Boolean {
        return bindRepository.auth(pass = pass, level = level)
    }

    suspend fun auth(num: String, appUuid: String): Boolean {
        return bindRepository.auth(num = num, appUuid = appUuid)
    }

}