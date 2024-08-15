package ru.cityron.domain.usecase.all.settings.http

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.cityron.data.room.all.settings.M3SettingsDatabase
import ru.cityron.data.room.all.settings.http.fromDto
import ru.cityron.domain.model.Http
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetM3SettingsHttpUseCase @Inject constructor(
    private val m3SettingsDatabase: M3SettingsDatabase
) {

    private val scope = CoroutineScope(Dispatchers.IO)

    suspend operator fun invoke() = m3SettingsDatabase.httpDao.fetchSettingsHttp().fromDto()

    val flow = m3SettingsDatabase.httpDao.listenSettingsHttp()
        .map { it.fromDto() }
        .distinctUntilChanged()
        .stateIn(
            scope = scope,
            started = SharingStarted.Lazily,
            initialValue = Http()
        )

}