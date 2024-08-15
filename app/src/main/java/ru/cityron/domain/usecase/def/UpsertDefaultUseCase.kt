package ru.cityron.domain.usecase.def

import android.os.Build
import ru.cityron.data.room.device.DeviceDto
import ru.cityron.domain.model.Filter
import ru.cityron.domain.model.m3.M3All
import ru.cityron.domain.usecase.all.UpsertM3AllUseCase
import ru.cityron.domain.usecase.device.UpsertDeviceUseCase
import ru.cityron.domain.usecase.events.UpsertFilterUseCase
import ru.cityron.domain.utils.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpsertDefaultUseCase @Inject constructor(
    private val upsertM3AllUseCase: UpsertM3AllUseCase,
    private val upsertFilterUseCase: UpsertFilterUseCase,
    private val upsertDeviceUseCase: UpsertDeviceUseCase,
) {

    suspend operator fun invoke() {
        upsertDeviceUseCase(DeviceDto(
            deviceId = UUID.generate(),
            deviceName = "${Build.MANUFACTURER}_${Build.MODEL}"
        ))
        upsertM3AllUseCase(M3All())
        upsertFilterUseCase(Filter())
    }

}