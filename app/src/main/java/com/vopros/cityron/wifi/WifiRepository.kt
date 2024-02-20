package com.vopros.cityron.wifi

import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WifiRepository @Inject constructor(
    @ApplicationContext private val ctx: Context
) {

    private val wifiManager = ctx.getSystemService(WifiManager::class.java)
    private val connectivityManager = ctx.getSystemService(ConnectivityManager::class.java)

    suspend fun isInLocalNetwork(): Flow<Boolean> {
        return flow {
            when (!wifiManager.isWifiEnabled) {
                true -> emit(false)
                else -> emit(connectivityManager.activeNetwork != null)
            }
        }
    }

}