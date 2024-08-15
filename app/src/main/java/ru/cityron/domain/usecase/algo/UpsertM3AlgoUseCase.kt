package ru.cityron.domain.usecase.algo

import ru.cityron.data.room.all.Table
import ru.cityron.data.room.all.algo.electric.AlgoElectricDto
import ru.cityron.data.room.all.algo.fan.AlgoFanDto
import ru.cityron.data.room.all.algo.other.AlgoOtherDto
import ru.cityron.data.room.all.algo.pi.AlgoPiDto
import ru.cityron.data.room.all.algo.timings.AlgoTimingDto
import ru.cityron.data.room.all.algo.water.AlgoWaterDto
import ru.cityron.domain.model.m3.M3All
import ru.cityron.domain.usecase.algo.electric.UpsertAlgoElectricUseCase
import ru.cityron.domain.usecase.algo.fan.UpsertAlgoFanUseCase
import ru.cityron.domain.usecase.algo.other.UpsertAlgoOtherUseCase
import ru.cityron.domain.usecase.algo.pi.UpsertAlgoPiUseCase
import ru.cityron.domain.usecase.algo.timing.UpsertAlgoTimingUseCase
import ru.cityron.domain.usecase.algo.water.UpsertAlgoWaterUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpsertM3AlgoUseCase @Inject constructor(
    private val upsertAlgoPiUseCase: UpsertAlgoPiUseCase,
    private val upsertAlgoFanUseCase: UpsertAlgoFanUseCase,
    private val upsertAlgoOtherUseCase: UpsertAlgoOtherUseCase,
    private val upsertAlgoWaterUseCase: UpsertAlgoWaterUseCase,
    private val upsertAlgoTimingUseCase: UpsertAlgoTimingUseCase,
    private val upsertAlgoElectricUseCase: UpsertAlgoElectricUseCase,
) {

    suspend operator fun invoke(all: M3All) {
        upsertPi(all)
        upsertFan(all)
        upsertOther(all)
        upsertWater(all)
        upsertTiming(all)
        upsertElectric(all)
    }

    private suspend fun upsertPi(all: M3All) {
        upsertAlgoPiUseCase(
            AlgoPiDto(
                id = Table.ID,
                piAutoEn = all.settings.algo.piAutoEn,

                piKofP = all.settings.algo.piKofP,
                piKofPMin = all.static.settingsMin.algo.piKofP,
                piKofPMax = all.static.settingsMax.algo.piKofP,

                piKofI = all.settings.algo.piKofI,
                piKofIMin = all.static.settingsMin.algo.piKofI,
                piKofIMax = all.static.settingsMax.algo.piKofI,

                piErr = all.settings.algo.piErr,
                piErrMin = all.static.settingsMin.algo.piErr,
                piErrMax = all.static.settingsMax.algo.piErr,
            )
        )
        upsertAlgoPiUseCase(
            AlgoPiDto(
                id = Table.ID_MIN,
                piAutoEn = null,

                piKofP = all.settings.algo.pi2KofP,
                piKofPMin = all.static.settingsMin.algo.pi2KofP,
                piKofPMax = all.static.settingsMax.algo.pi2KofP,

                piKofI = all.settings.algo.pi2KofI,
                piKofIMin = all.static.settingsMin.algo.pi2KofI,
                piKofIMax = all.static.settingsMax.algo.pi2KofI,

                piErr = all.settings.algo.pi2Err,
                piErrMin = all.static.settingsMin.algo.pi2Err,
                piErrMax = all.static.settingsMax.algo.pi2Err,
            )
        )
    }

    private suspend fun upsertFan(all: M3All) {
        upsertAlgoFanUseCase(
            AlgoFanDto(
                id = Table.ID,
                fanSpeedMin = all.settings.algo.fan1SpeedMin,
                fanSpeedMinMin = all.static.settingsMin.algo.fan1SpeedMin,
                fanSpeedMinMax = all.static.settingsMax.algo.fan1SpeedMin,

                fanSpeedMax = all.settings.algo.fan1SpeedMax,
                fanSpeedMaxMin = all.static.settingsMin.algo.fan1SpeedMax,
                fanSpeedMaxMax = all.static.settingsMax.algo.fan1SpeedMax,
            )
        )
        upsertAlgoFanUseCase(
            AlgoFanDto(
                id = Table.ID_MIN,
                fanSpeedMin = all.settings.algo.fan2SpeedMin,
                fanSpeedMinMin = all.static.settingsMin.algo.fan2SpeedMin,
                fanSpeedMinMax = all.static.settingsMax.algo.fan2SpeedMin,

                fanSpeedMax = all.settings.algo.fan2SpeedMax,
                fanSpeedMaxMin = all.static.settingsMin.algo.fan2SpeedMax,
                fanSpeedMaxMax = all.static.settingsMax.algo.fan2SpeedMax,
            )
        )
    }

    private suspend fun upsertOther(all: M3All) {
        upsertAlgoOtherUseCase(
            AlgoOtherDto(
                tempControl = all.settings.algo.tempControl,
                filterEn = all.settings.algo.filterEn,
                autoStartEn = all.settings.algo.autoStartEn,
                isDistPower = all.settings.algo.isDistPower,
                alarmRestartCount = all.settings.algo.alarmRestartCount,
                alarmRestartCountMin = all.static.settingsMin.algo.alarmRestartCount,
                alarmRestartCountMax = all.static.settingsMax.algo.alarmRestartCount,
            )
        )
    }

    private suspend fun upsertWater(all: M3All) {
        upsertAlgoWaterUseCase(
            AlgoWaterDto(
                modeZimaLetoSource = all.settings.algo.modeZimaLetoSource,
                modeZimaLetoUser = all.settings.algo.modeZimaLetoUser,

                timeWarmUp = all.settings.algo.timeWarmUp,
                timeWarmUpMin = all.static.settingsMin.algo.timeWarmUp,
                timeWarmUpMax = all.static.settingsMax.algo.timeWarmUp,

                timeDefrost = all.settings.algo.timeDefrost,
                timeDefrostMin = all.static.settingsMin.algo.timeDefrost,
                timeDefrostMax = all.static.settingsMax.algo.timeDefrost,
            )
        )
    }

    private suspend fun upsertTiming(all: M3All) {
        upsertAlgoTimingUseCase(
            AlgoTimingDto(
                timeOpenDamper = all.settings.algo.timeOpenDamper,
                timeOpenDamperMin = all.static.settingsMin.algo.timeOpenDamper,
                timeOpenDamperMax = all.static.settingsMax.algo.timeOpenDamper,

                timeAccelerFan = all.settings.algo.timeAccelerFan,
                timeAccelerFanMin = all.static.settingsMin.algo.timeAccelerFan,
                timeAccelerFanMax = all.static.settingsMax.algo.timeAccelerFan,

                timeBlowHeat = all.settings.algo.timeBlowHeat,
                timeBlowHeatMin = all.static.settingsMin.algo.timeBlowHeat,
                timeBlowHeatMax = all.static.settingsMax.algo.timeBlowHeat,
            )
        )
    }

    private suspend fun upsertElectric(all: M3All) {
        upsertAlgoElectricUseCase(
            AlgoElectricDto(
                heatPwmPeriod = all.settings.algo.heatPwmPeriod,
                heatPwmPeriodMin = all.static.settingsMin.algo.heatPwmPeriod,
                heatPwmPeriodMax = all.static.settingsMax.algo.heatPwmPeriod,
            )
        )
    }

}