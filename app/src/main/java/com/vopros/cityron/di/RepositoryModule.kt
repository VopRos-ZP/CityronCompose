package com.vopros.cityron.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.cityron.core.data.repository.*
import ru.cityron.core.domain.repository.*
import ru.cityron.m3.data.repository.M3ControllerRepositoryImpl
import ru.cityron.m3.domain.repository.M3ControllerRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindEventsRepository(eventsRepositoryImpl: EventsRepositoryImpl): EventsRepository

    @Binds
    fun bindConfRepository(confRepositoryImpl: ConfRepositoryImpl): ConfRepository

    @Binds
    @Singleton
    fun bindCControllerRepository(cControllerRepositoryImpl: CControllerRepositoryImpl): CControllerRepository

    @Binds
    fun bindConnectivityRepository(connectivityRepositoryImpl: ConnectivityRepositoryImpl): ConnectivityRepository

    @Binds
    fun bindControllerRepository(controllerRepositoryImpl: ControllerRepositoryImpl): ControllerRepository

    @Binds
    @Singleton
    fun bindLocalStoreRepository(localStoreRepositoryImpl: LocalStoreRepositoryImpl): LocalStoreRepository

    @Binds
    fun bindNetworkRepository(networkRepositoryImpl: NetworkRepositoryImpl): NetworkRepository

    @Binds
    fun bindWifiRepository(wifiRepositoryImpl: WifiRepositoryImpl): WifiRepository

    @Binds
    fun bindUdpRepository(udpRepositoryImpl: UdpRepositoryImpl): UdpRepository

    @Binds
    fun bindM3ControllerRepository(m3ControllerRepositoryImpl: M3ControllerRepositoryImpl): M3ControllerRepository

}