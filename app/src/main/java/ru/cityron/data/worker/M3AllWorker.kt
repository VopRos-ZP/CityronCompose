package ru.cityron.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import ru.cityron.domain.repository.M3Repository
import ru.cityron.domain.usecase.all.UpsertM3AllUseCase
import javax.inject.Inject

@HiltWorker
class M3AllWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val m3Repository: M3Repository,
    private val upsertM3AllUseCase: UpsertM3AllUseCase
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        m3Repository.all.collect {
            upsertM3AllUseCase(it)
        }
        return Result.success()
    }

    companion object {
        class Factory @Inject constructor(
            private val m3Repository: M3Repository,
            private val upsertM3AllUseCase: UpsertM3AllUseCase
        ) : WorkerFactory() {
            override fun createWorker(
                appContext: Context,
                workerClassName: String,
                workerParameters: WorkerParameters
            ): ListenableWorker = M3AllWorker(
                appContext,
                workerParameters,
                m3Repository = m3Repository,
                upsertM3AllUseCase = upsertM3AllUseCase
            )
        }
    }

}