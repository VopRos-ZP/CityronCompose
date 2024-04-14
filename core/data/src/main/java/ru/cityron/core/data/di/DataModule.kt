package ru.cityron.core.data.di

import android.content.Context
import com.fingerprintjs.android.fingerprint.Fingerprinter
import com.fingerprintjs.android.fingerprint.FingerprinterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideFingerprint(
        @ApplicationContext context: Context
    ): Fingerprinter = FingerprinterFactory.create(context)

    @Provides
    fun provideHttpClient() = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

}