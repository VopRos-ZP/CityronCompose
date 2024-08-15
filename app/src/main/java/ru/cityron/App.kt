package ru.cityron

import android.app.Application
import android.util.Log
import androidx.work.Configuration
import androidx.work.WorkManager
import dagger.hilt.android.HiltAndroidApp
import ru.cityron.data.worker.DataSourceWorker
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var workerFactory: DataSourceWorker.Companion.Factory

    override fun onCreate() {
        super.onCreate()

        WorkManager.initialize(
            this,
            Configuration.Builder()
                .setMinimumLoggingLevel(Log.DEBUG)
                .setWorkerFactory(workerFactory)
                .build()
        )
    }

}