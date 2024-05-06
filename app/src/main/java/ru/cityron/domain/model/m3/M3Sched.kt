package ru.cityron.domain.model.m3

import kotlinx.serialization.Serializable

@Serializable
data class M3Sched(
    val task0: M3Task = M3Task(),
    val task1: M3Task = M3Task(),
    val task2: M3Task = M3Task(),
    val task3: M3Task = M3Task(),
    val task4: M3Task = M3Task(),
    val task5: M3Task = M3Task(),
    val task6: M3Task = M3Task(),
    val task7: M3Task = M3Task(),
    val task8: M3Task = M3Task(),
    val task9: M3Task = M3Task()
)