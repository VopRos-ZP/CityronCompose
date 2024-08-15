package ru.cityron.domain.usecase.all.settings.other

import ru.cityron.data.room.all.settings.M3SettingsDatabase
import ru.cityron.data.room.all.settings.other.fromDto
import ru.cityron.domain.model.Others
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpsertM3SettingsOtherUseCase @Inject constructor(
    private val m3SettingsDatabase: M3SettingsDatabase
) {

    suspend operator fun invoke(others: Others) {
        m3SettingsDatabase.otherDao.upsert(others.fromDto())
    }

}