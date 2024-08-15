package ru.cityron

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import dagger.hilt.android.AndroidEntryPoint
import ru.cityron.data.worker.DataSourceWorker
import ru.cityron.presentation.screens.root.RootScreen
import ru.cityron.ui.theme.CityronTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WorkManager.getInstance(this).beginUniqueWork(
            "data_source", ExistingWorkPolicy.REPLACE,
            OneTimeWorkRequest.from(DataSourceWorker::class.java)
        ).enqueue()

        setContent {
            CityronTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    RootScreen()
                }
            }
        }
    }

}
