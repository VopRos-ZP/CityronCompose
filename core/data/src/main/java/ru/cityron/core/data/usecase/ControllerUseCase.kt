package ru.cityron.core.data.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.cityron.core.data.mappers.toController
import ru.cityron.core.domain.model.Controller
import ru.cityron.core.domain.model.Info
import ru.cityron.core.domain.repository.CControllerRepository
import ru.cityron.core.domain.repository.ConnectivityRepository
import ru.cityron.core.domain.repository.ControllerRepository
import ru.cityron.core.domain.utils.Resource
import javax.inject.Inject

class ControllerUseCase @Inject constructor(
    private val controllerRepository: ControllerRepository,
    private val cControllerRepository: CControllerRepository,
    private val connectivityRepository: ConnectivityRepository,
    //private val bindRepository: BindRepository,
) {

    fun fetchAll(): Flow<Resource<List<Pair<Controller, Int>>>> = flow {
        emit(Resource.Loading(true))
        try {
            val controllers = controllerRepository.fetchAllController().map {
                val old = cControllerRepository.controller?.copy()
                cControllerRepository.controller = it
                val status = if (connectivityRepository.isConnected()) 0 else 2
                cControllerRepository.controller = old
                it to status
            }
            emit(Resource.Success(controllers))
            emit(Resource.Loading(false))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }

    fun addController(info: Info): Flow<Boolean> = flow {
        controllerRepository.upsertController(info.toController())
    }

    fun removeController(): Flow<Boolean> = flow {
        cControllerRepository.controller?.let {
            controllerRepository.deleteController(it)
        }
    }

}