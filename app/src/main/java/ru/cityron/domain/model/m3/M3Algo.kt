package ru.cityron.domain.model.m3

import kotlinx.serialization.Serializable

@Serializable
data class M3Algo(
    val alarmRestartCount: Int = 0,
    val autoStartEn: Int = 0,
    val fan1SpeedMax: Int = 0,
    val fan1SpeedMin: Int = 0,
    val fan2SpeedMax: Int = 0,
    val fan2SpeedMin: Int = 0,
    val filterEn: Int = 0,
    val heatPwmPeriod: Int = 0,
    val isDistPower: Int = 0,
    val modeZimaLetoGist: Int = 0,
    val modeZimaLetoSource: Int = 0,
    val modeZimaLetoTemp: Int = 0,
    val modeZimaLetoUser: Int = 0,
    val nanosRollTime: Int = 0,
    val pi2Err: Int = 0,
    val pi2KofI: Int = 0,
    val pi2KofP: Int = 0,
    val piAutoEn: Int = 0,
    val piErr: Int = 0,
    val piKofI: Int = 0,
    val piKofP: Int = 0,
    val tempControl: Int = 0,
    val tempRertunWaterWait: Int = 0,
    val timeAccelerFan: Int = 0,
    val timeBlowHeat: Int = 0,
    val timeDefrost: Int = 0,
    val timeOpenDamper: Int = 0,
    val timeWarmUp: Int = 0
)