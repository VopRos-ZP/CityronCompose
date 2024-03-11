package ru.cityron.network

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.cityron.network.impl.LocalControllerStateRepositoryImpl
import ru.cityron.network.impl.LocalStateRepo
import ru.cityron.network.impl.ServerControllerStateRepositoryImpl
import ru.cityron.network.impl.ServerStateRepo

@Module
@InstallIn(SingletonComponent::class)
interface NetworkModule {

    @Binds
    @LocalStateRepo
    fun bindLocalControllerStateRepository(localControllerStateRepositoryImpl: LocalControllerStateRepositoryImpl) : ControllerStateRepository

    @Binds
    @ServerStateRepo
    fun bindServerControllerStateRepository(serverControllerStateRepositoryImpl: ServerControllerStateRepositoryImpl) : ControllerStateRepository


}