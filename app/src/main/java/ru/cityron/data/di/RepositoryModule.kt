package ru.cityron.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.cityron.data.repository.CheckIpRepositoryImpl
import ru.cityron.data.repository.ConfRepositoryImpl
import ru.cityron.data.repository.ConnectivityRepositoryImpl
import ru.cityron.data.repository.ControllerRepositoryImpl
import ru.cityron.data.repository.CurrentRepositoryImpl
import ru.cityron.data.repository.HttpRepositoryImpl
import ru.cityron.data.repository.IpRepositoryImpl
import ru.cityron.data.repository.M3RepositoryImpl
import ru.cityron.data.repository.MetricRepositoryImpl
import ru.cityron.data.repository.NetworkRepositoryImpl
import ru.cityron.data.repository.UdpRepositoryImpl
import ru.cityron.domain.repository.CheckIpRepository
import ru.cityron.domain.repository.ConfRepository
import ru.cityron.domain.repository.ConnectivityRepository
import ru.cityron.domain.repository.ControllerRepository
import ru.cityron.domain.repository.CurrentRepository
import ru.cityron.domain.repository.HttpRepository
import ru.cityron.domain.repository.IpRepository
import ru.cityron.domain.repository.M3Repository
import ru.cityron.domain.repository.MetricRepository
import ru.cityron.domain.repository.NetworkRepository
import ru.cityron.domain.repository.UdpRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindControllerRepository(controllerRepositoryImpl: ControllerRepositoryImpl):  ControllerRepository

    @Binds
    @Singleton
    fun bindUdpRepository(udpRepositoryImpl: UdpRepositoryImpl): UdpRepository

    @Binds
    @Singleton
    fun bindHttpRepository(httpRepositoryImpl: HttpRepositoryImpl): HttpRepository

    @Binds
    @Singleton
    fun bindConnectivityRepository(connectivityRepositoryImpl: ConnectivityRepositoryImpl): ConnectivityRepository

    @Binds
    @Singleton
    fun bindCurrentRepository(currentRepositoryImpl: CurrentRepositoryImpl): CurrentRepository

    @Binds
    @Singleton
    fun bindM3Repository(m3RepositoryImpl: M3RepositoryImpl): M3Repository

    @Binds
    @Singleton
    fun bindNetworkRepository(networkRepositoryImpl: NetworkRepositoryImpl): NetworkRepository

    @Binds
    @Singleton
    fun bindMetricRepository(metricRepositoryImpl: MetricRepositoryImpl): MetricRepository

    @Binds
    @Singleton
    fun bindCheckIpRepository(checkIpRepositoryImpl: CheckIpRepositoryImpl): CheckIpRepository

    @Binds
    @Singleton
    fun bindIpRepository(ipRepositoryImpl: IpRepositoryImpl): IpRepository

    @Binds
    @Singleton
    fun bindConfRepository(confRepositoryImpl: ConfRepositoryImpl): ConfRepository

}