package ru.cityron.domain.usecase.all.sched

import ru.cityron.data.room.all.sched.M3SchedDatabase
import ru.cityron.data.room.all.sched.toDto
import ru.cityron.domain.model.m3.M3Task
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpsertM3TaskUseCase @Inject constructor(
    private val m3SchedDatabase: M3SchedDatabase
) {

    suspend operator fun invoke(tasks: List<M3Task>) {
        tasks.forEach { m3SchedDatabase.dao.upsertM3Sched(it.toDto()) }
    }

}