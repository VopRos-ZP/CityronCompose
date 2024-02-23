package com.vopros.cityron.di

import com.vopros.cityron.repository.controllerState.ServerControllerStateRepositoryImpl
import com.vopros.cityron.repository.controllerState.ControllerStateRepository
import com.vopros.cityron.repository.controllerState.LocalControllerStateRepositoryImpl
import com.vopros.cityron.repository.controllerState.LocalStateRepo
import com.vopros.cityron.repository.controllerState.ServerStateRepo
import com.vopros.cityron.repository.wifi.WifiRepository
import com.vopros.cityron.repository.wifi.WifiRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindWifiRepository(wifiRepositoryImpl: WifiRepositoryImpl) : WifiRepository

    @Binds
    @LocalStateRepo
    fun bindLocalControllerStateRepository(localControllerStateRepositoryImpl: LocalControllerStateRepositoryImpl) : ControllerStateRepository

    @Binds
    @ServerStateRepo
    fun bindServerControllerStateRepository(serverControllerStateRepositoryImpl: ServerControllerStateRepositoryImpl) : ControllerStateRepository

}