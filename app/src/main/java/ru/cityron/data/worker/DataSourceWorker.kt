package ru.cityron.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import ru.cityron.domain.model.DataSource
import ru.cityron.domain.model.Status
import ru.cityron.domain.model.m3.M3All
import ru.cityron.domain.repository.CurrentRepository
import ru.cityron.domain.repository.HttpRepository
import ru.cityron.domain.usecase.all.UpsertM3AllUseCase
import ru.cityron.domain.usecase.controller.GetControllersUseCase
import ru.cityron.domain.usecase.controller.UpsertControllerUseCase
import ru.cityron.domain.utils.Path.JSON_ALL
import ru.cityron.domain.utils.Path.JSON_STATE
import ru.cityron.domain.utils.fromJson
import javax.inject.Inject

@HiltWorker
class DataSourceWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val httpRepository: HttpRepository,
    private val currentRepository: CurrentRepository,
    private val upsertM3AllUseCase: UpsertM3AllUseCase,
    private val getControllersUseCase: GetControllersUseCase,
    private val upsertControllerUseCase: UpsertControllerUseCase,
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        while (true) {
            val controllers = getControllersUseCase()
            controllers.forEach {
                val (all, source) = try {
                    fromJson<M3All>(httpRepository.get("http://${it.ipAddress}/$JSON_ALL")) to DataSource.LOCAL
                } catch (_: Exception) {
                    try {
                        fromJson<M3All>(httpRepository.get("https://rcserver.ru/rc/${it.idCpu}/$JSON_STATE")) to DataSource.REMOTE
                    } catch (_: Exception) {
                        null to DataSource.LOCAL
                    }
                }
                val status = when (all) {
                    null -> Status.Offline
                    else -> {
                        if (currentRepository.current.value == it) {
                            upsertM3AllUseCase(all)
                        }
                        when (all.state.alarms) {
                            0 -> Status.Online(source)
                            else -> Status.Alert(source)
                        }
                    }
                }
                upsertControllerUseCase(it.copy(status = status))
            }
            delay(1000)
        }
        return Result.success()
    }

    companion object {

        class Factory @Inject constructor(
            private val httpRepository: HttpRepository,
            private val currentRepository: CurrentRepository,
            private val upsertM3AllUseCase: UpsertM3AllUseCase,
            private val getControllersUseCase: GetControllersUseCase,
            private val upsertControllerUseCase: UpsertControllerUseCase,
        ) : WorkerFactory() {
            override fun createWorker(
                appContext: Context,
                workerClassName: String,
                workerParameters: WorkerParameters
            ): ListenableWorker = DataSourceWorker(
                context = appContext,
                workerParameters = workerParameters,
                httpRepository = httpRepository,
                currentRepository = currentRepository,
                upsertM3AllUseCase = upsertM3AllUseCase,
                getControllersUseCase = getControllersUseCase,
                upsertControllerUseCase = upsertControllerUseCase
            )
        }

    }

}