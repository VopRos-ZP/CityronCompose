package ru.cityron.domain.usecase.all.stat

import ru.cityron.data.room.all.stat.M3StaticDatabase
import ru.cityron.data.room.all.stat.toDto
import ru.cityron.domain.model.m3.M3Static
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpsertM3StaticUseCase @Inject constructor(
    private val m3StaticDatabase: M3StaticDatabase
) {

    suspend operator fun invoke(static: M3Static) {
        m3StaticDatabase.dao.upsertM3Static(static.toDto())
    }

}