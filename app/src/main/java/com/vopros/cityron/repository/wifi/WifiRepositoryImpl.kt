package com.vopros.cityron.repository.wifi

import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WifiRepositoryImpl @Inject constructor(
    @ApplicationContext private val ctx: Context
) : WifiRepository {

    private val wifiManager = ctx.getSystemService(WifiManager::class.java)
    private val connectivityManager = ctx.getSystemService(ConnectivityManager::class.java)

    override suspend fun isInLocalNetwork(): Flow<Boolean> {
        return flow {
            when (!wifiManager.isWifiEnabled) {
                true -> emit(false)
                else -> emit(connectivityManager.activeNetwork != null)
            }
        }
    }

}