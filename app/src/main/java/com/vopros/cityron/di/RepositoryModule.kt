package com.vopros.cityron.di

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

}