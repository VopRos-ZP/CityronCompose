package com.vopros.cityron.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.cityron.domain.model.local.Settings
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LocalStore @Inject constructor(@ApplicationContext private val ctx: Context) {

    companion object {
        val Context.localStore: DataStore<Settings> by dataStore(
            fileName = "settings.pb",
            serializer = SettingsSerializer
        )
    }

    val data = ctx.localStore.data

    suspend fun update(transform: suspend Settings.() -> Settings) {
        ctx.localStore.updateData(transform)
    }

}