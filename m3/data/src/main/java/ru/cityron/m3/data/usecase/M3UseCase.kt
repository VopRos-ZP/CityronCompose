package ru.cityron.m3.data.usecase

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.cityron.core.domain.utils.Resource
import ru.cityron.m3.domain.model.M3All
import ru.cityron.m3.domain.repository.M3ControllerRepository
import javax.inject.Inject

class M3UseCase @Inject constructor(
    private val m3ControllerRepository: M3ControllerRepository
) {

    private var isFetching: Boolean = false

    fun fetchAll(): Flow<Resource<M3All>> {
        isFetching = true
        return flow {
            emit(Resource.Loading(true))
            while (isFetching) {
                m3ControllerRepository.fetchAll()
                    .onFailure {
                        emit(Resource.Error(it.localizedMessage))
                        emit(Resource.Loading(false))
                    }
                    .onSuccess {
                        emit(Resource.Success(it))
                        emit(Resource.Loading(false))
                    }
                delay(1000)
            }
        }
    }

    fun stopFetching() {
        isFetching = false
    }

}