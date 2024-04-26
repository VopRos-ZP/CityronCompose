package ru.cityron.domain.model.m3

import kotlinx.serialization.Serializable

@Serializable
data class M3Algo(
    val alarmRestartCount: Int,
    val autoStartEn: Int,
    val fan1SpeedMax: Int,
    val fan1SpeedMin: Int,
    val fan2SpeedMax: Int,
    val fan2SpeedMin: Int,
    val filterEn: Int,
    val heatPwmPeriod: Int,
    val isDistPower: Int,
    val modeZimaLetoGist: Int,
    val modeZimaLetoSource: Int,
    val modeZimaLetoTemp: Int,
    val modeZimaLetoUser: Int,
    val nanosRollTime: Int,
    val pi2Err: Int,
    val pi2KofI: Int,
    val pi2KofP: Int,
    val piAutoEn: Int,
    val piErr: Int,
    val piKofI: Int,
    val piKofP: Int,
    val tempControl: Int,
    val tempRertunWaterWait: Int,
    val timeAccelerFan: Int,
    val timeBlowHeat: Int,
    val timeDefrost: Int,
    val timeOpenDamper: Int,
    val timeWarmUp: Int
)