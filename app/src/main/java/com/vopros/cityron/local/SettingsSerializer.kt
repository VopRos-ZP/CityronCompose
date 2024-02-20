package com.vopros.cityron.local

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.cityron.domain.model.local.Settings
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

object SettingsSerializer : Serializer<Settings> {
    override val defaultValue: Settings = Settings("", emptyList())

    override suspend fun readFrom(input: InputStream): Settings {
        try {
            return Json.decodeFromString(input.readBytes().decodeToString())
        } catch (exception: SerializationException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: Settings, output: OutputStream) = output.write(
        Json.encodeToString(t).encodeToByteArray()
    )

}