package ru.cityron.presentation.screens.algo.timings

data class AlgoTimingsViewState(
    val timeOpenDamperOld: Int = 30,
    val timeOpenDamper: Int = timeOpenDamperOld,
    val timeOpenDamperRange: IntRange = (5..90),
    val timeOpenDamperInRange: Boolean = true,

    val timeAccelerFanOld: Int = 10,
    val timeAccelerFan: Int = timeAccelerFanOld,
    val timeAccelerFanRange: IntRange = (1..60),
    val timeAccelerFanInRange: Boolean = true,

    val timeBlowHeatOld: Int = 10,
    val timeBlowHeat: Int = timeBlowHeatOld,
    val timeBlowHeatRange: IntRange = (1..60),
    val timeBlowHeatInRange: Boolean = true,

    val isChanged: Boolean = false,
)
