package ru.cityron.domain.usecase.all.settings.eth

import ru.cityron.data.room.all.settings.M3SettingsDatabase
import ru.cityron.data.room.all.settings.eth.toDto
import ru.cityron.domain.model.Eth
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpsertM3SettingsEthUseCase @Inject constructor(
    private val m3SettingsDatabase: M3SettingsDatabase
) {

    suspend operator fun invoke(eth: Eth) {
        m3SettingsDatabase.ethDao.upsertSettingsEth(eth.toDto())
    }

}