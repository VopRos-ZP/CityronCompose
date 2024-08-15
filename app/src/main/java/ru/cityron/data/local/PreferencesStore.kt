package ru.cityron.data.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesStore @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    companion object {
        val Context.eventsStore by preferencesDataStore(name = "events")

        private val FIRST_LAUNCH_KEY = booleanPreferencesKey(name = "first_launch")
    }

    val isFirstLaunch: Flow<Boolean> = context.eventsStore.data.map { it[FIRST_LAUNCH_KEY] ?: true }

    suspend fun setFirstLaunch(value: Boolean) {
        context.eventsStore.edit { it[FIRST_LAUNCH_KEY] = value }
    }

}
