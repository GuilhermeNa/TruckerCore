package com.example.truckercore.model.infrastructure.connectivity

import android.net.ConnectivityManager
import android.net.Network
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class ConnectivityRepository(private val connectivityManager: ConnectivityManager) {

    fun isInternetAvailableFlow(): Flow<Boolean> = callbackFlow {
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                trySend(true)
            }

            override fun onLost(network: Network) {
                trySend(false)
            }
        }

        connectivityManager.registerDefaultNetworkCallback(networkCallback)

        awaitClose { connectivityManager.unregisterNetworkCallback(networkCallback) }
    }
}