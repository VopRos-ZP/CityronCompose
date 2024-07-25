package ru.cityron.presentation.screens.algo.timings

data class AlgoTimingsViewState(
    val timeOpenDamperOld: Int,
    val timeOpenDamper: Int,
    val timeAccelerFanOld: Int,
    val timeAccelerFan: Int,
    val timeBlowHeatOld: Int,
    val timeBlowHeat: Int,
    val isChanged: Boolean = false,
)
