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
import ru.cityron.domain.usecase.auth.ServerAuthUseCase
import ru.cityron.domain.usecase.controller.GetControllersUseCase
import ru.cityron.domain.usecase.controller.UpsertControllerUseCase
import ru.cityron.domain.utils.Path.JSON_ALL
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
    private val serverAuthUseCase: ServerAuthUseCase,
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        while (true) {
            getControllersUseCase().forEach {
                val (all, source, token) = try {
                    Triple(
                        first = fromJson<M3All>(httpRepository.get(url = "http://${it.ipAddress}/$JSON_ALL")),
                        second = DataSource.LOCAL,
                        third = null
                    )
                } catch (_: Exception) {
                    try {
                        serverAuthUseCase(it)
                    } catch (_: Exception) {
                        Triple(
                            first = null,
                            second = DataSource.LOCAL,
                            third = null
                        )
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
                val name = when (all) {
                    null -> it.name
                    else -> "${all.static.devName.replaceFirstChar(Char::uppercaseChar)} (${all.settings.others.loc.ifEmpty { all.static.idUsr.uppercase() }})"
                }
                upsertControllerUseCase(
                    it.copy(
                        name = name,
                        status = status,
                        token = token
                    )
                )
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
            private val serverAuthUseCase: ServerAuthUseCase,
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
                upsertControllerUseCase = upsertControllerUseCase,
                serverAuthUseCase = serverAuthUseCase
            )
        }

    }

}