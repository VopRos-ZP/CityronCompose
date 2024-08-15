package ru.cityron.domain.usecase.device

import ru.cityron.data.room.device.DeviceDatabase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetDeviceUseCase @Inject constructor(
    private val deviceDatabase: DeviceDatabase
) {

    suspend operator fun invoke() = deviceDatabase.dao.fetch()

}