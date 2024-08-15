package ru.cityron.domain.usecase.device

import ru.cityron.data.room.device.DeviceDatabase
import ru.cityron.data.room.device.DeviceDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpsertDeviceUseCase @Inject constructor(
    private val deviceDatabase: DeviceDatabase
) {

    suspend operator fun invoke(device: DeviceDto) {
        deviceDatabase.dao.upsert(device)
    }

}