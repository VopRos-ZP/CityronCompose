package ru.cityron.core.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.fingerprintjs.android.fingerprint.Fingerprinter
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.cityron.core.domain.model.Setting
import ru.cityron.core.domain.repository.LocalStoreRepository
import javax.inject.Inject

class LocalStoreRepositoryImpl @Inject constructor(
    @ApplicationContext private val ctx: Context,
    fingerprint: Fingerprinter
) : LocalStoreRepository {

    init {
        fingerprint.getDeviceId(Fingerprinter.Version.latest) { res ->
            CoroutineScope(Dispatchers.Main).launch {
                emitSetting(Setting(res.deviceId))
            }
        }
    }

    companion object {
        val Context.localStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

        private val DEVICE_ID_KEY = stringPreferencesKey("device_id")
    }

    override val setting = ctx.localStore.data.map { Setting(it[DEVICE_ID_KEY] ?: "") }

    override suspend fun emitSetting(setting: Setting) {
        ctx.localStore.edit { it[DEVICE_ID_KEY] = setting.deviceId }
    }

}