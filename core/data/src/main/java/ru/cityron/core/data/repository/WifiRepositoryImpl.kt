package ru.cityron.core.data.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import ru.cityron.core.domain.repository.WifiRepository
import javax.inject.Inject

class WifiRepositoryImpl @Inject constructor(
    @ApplicationContext private val ctx: Context
) : WifiRepository {

    private val connectivityManager = ctx.getSystemService(ConnectivityManager::class.java)

    override suspend fun isInLocalNetwork(): Flow<Boolean> = callbackFlow {
        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                launch { send(true) }
            }

            override fun onLost(network: Network) {
                launch { send(false) }
            }
        }
        connectivityManager.registerDefaultNetworkCallback(callback)
        awaitClose {
            connectivityManager.unregisterNetworkCallback(callback)
        }
    }

}