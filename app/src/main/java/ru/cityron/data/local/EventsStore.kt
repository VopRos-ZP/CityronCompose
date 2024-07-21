package ru.cityron.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class EventsStore(private val context: Context) {

    companion object {
        val Context.eventsStore by preferencesDataStore(name = "events")

        private val COUNT_KEY = intPreferencesKey(name = "count")
        private val TYPES_KEY = intPreferencesKey(name = "types")
        private val REASONS_KEY = intPreferencesKey(name = "reasons")
        private val SOURCES_KEY = intPreferencesKey(name = "sources")
    }

    val count: Flow<Int> = context.eventsStore.data.map { it[COUNT_KEY] ?: 1 }
    val types: Flow<Int> = context.eventsStore.data.map { it[TYPES_KEY] ?: 0 }
    val reasons: Flow<Int> = context.eventsStore.data.map { it[REASONS_KEY] ?: 0 }
    val sources: Flow<Int> = context.eventsStore.data.map { it[SOURCES_KEY] ?: 0 }

    suspend fun setCount(value: Int) {
        context.eventsStore.edit { it[COUNT_KEY] = value }
    }

    suspend fun setTypes(value: Int) {
        context.eventsStore.edit { it[TYPES_KEY] = value }
    }

    suspend fun setReasons(value: Int) {
        context.eventsStore.edit { it[REASONS_KEY] = value }
    }

    suspend fun setSources(value: Int) {
        context.eventsStore.edit { it[SOURCES_KEY] = value }
    }

}
