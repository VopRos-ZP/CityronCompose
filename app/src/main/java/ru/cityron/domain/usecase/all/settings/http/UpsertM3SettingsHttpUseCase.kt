package ru.cityron.domain.usecase.all.settings.http

import ru.cityron.data.room.all.settings.M3SettingsDatabase
import ru.cityron.data.room.all.settings.http.toDto
import ru.cityron.domain.model.Http
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpsertM3SettingsHttpUseCase @Inject constructor(
    private val m3SettingsDatabase: M3SettingsDatabase
) {

    suspend operator fun invoke(http: Http) {
        m3SettingsDatabase.httpDao.upsertSettingsHttp(http.toDto())
    }

}