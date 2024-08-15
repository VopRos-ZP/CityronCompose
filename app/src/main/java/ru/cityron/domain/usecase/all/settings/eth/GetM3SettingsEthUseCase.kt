package ru.cityron.domain.usecase.all.settings.eth

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.cityron.data.room.all.settings.M3SettingsDatabase
import ru.cityron.data.room.all.settings.eth.fromDto
import ru.cityron.domain.model.Eth
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetM3SettingsEthUseCase @Inject constructor(
    private val m3SettingsDatabase: M3SettingsDatabase
) {

    private val scope = CoroutineScope(Dispatchers.IO)

    suspend operator fun invoke() = m3SettingsDatabase.ethDao.fetchSettingsEth().fromDto()

    val flow = m3SettingsDatabase.ethDao.listenSettingsEth()
        .map { it.fromDto() }
        .distinctUntilChanged()
        .stateIn(
            scope = scope,
            started = SharingStarted.Lazily,
            initialValue = Eth()
        )

}