package ru.cityron.core.data.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.cityron.core.domain.model.Info
import ru.cityron.core.domain.repository.ControllerRepository
import ru.cityron.core.domain.repository.UdpRepository
import ru.cityron.core.domain.utils.Resource
import javax.inject.Inject

class UdpUseCase @Inject constructor(
    private val udpRepository: UdpRepository,
    private val controllerRepository: ControllerRepository,
) {

    fun send(): Flow<Resource<List<Pair<Info, Boolean>>>> = flow {
        emit(Resource.Loading(true))
        try {
            val added = controllerRepository.fetchAllController().map { it.idCpu }
            val list = mutableListOf<Info>()
            while (true) {
                val info = udpRepository.receive().await()
                if (!list.contains(info)) {
                    list.add(info)
                }
                emit(Resource.Success(list.map { i -> i to added.contains(i.idCpu) }))
                emit(Resource.Loading(false))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }

}