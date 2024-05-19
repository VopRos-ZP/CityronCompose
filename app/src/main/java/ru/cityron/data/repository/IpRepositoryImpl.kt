package ru.cityron.data.repository

import ru.cityron.data.room.ip.IpDatabase
import ru.cityron.data.room.ip.IpDto
import ru.cityron.data.room.ip.toDto
import ru.cityron.data.room.ip.toIp
import ru.cityron.domain.model.Ip
import ru.cityron.domain.repository.IpRepository
import javax.inject.Inject

class IpRepositoryImpl @Inject constructor(
    private val ipDatabase: IpDatabase
) : IpRepository {

    override suspend fun fetchAll(): List<Ip> {
        return ipDatabase.dao.fetchAll().map(IpDto::toIp)
    }

    override suspend fun upsert(ip: Ip) {
        ipDatabase.dao.upsert(ip.toDto())
    }

    override suspend fun delete(ip: Ip) {
        ipDatabase.dao.delete(ip.toDto())
    }

}