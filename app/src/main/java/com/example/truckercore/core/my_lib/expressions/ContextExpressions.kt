package com.example.truckercore.core.my_lib.expressions

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

/**
 * Checks whether the device currently has an active internet connection.
 * @return `true` if the device is connected to a validated internet connection; `false` otherwise.
 */
fun Context.isInternetAvailable(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    val network = connectivityManager.activeNetwork
    val networkCapabilities = connectivityManager.getNetworkCapabilities(network)

    return networkCapabilities?.let { netCap ->
        netCap.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                && netCap.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    } ?: false
}