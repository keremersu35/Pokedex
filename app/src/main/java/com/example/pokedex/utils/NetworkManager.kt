package com.example.pokedex.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.core.content.ContextCompat

class NetworkManager {

    private val networkRequest: NetworkRequest = NetworkRequest.Builder()
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .build()

    fun networkStateManager(
        context: Context,
        onLost: () -> Unit = {},
        onAvailable: () -> Unit = {},
        onUnAvailable: () -> Unit = {}
    ){
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            // network is available for use
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                onAvailable()
            }

            override fun onUnavailable() {
                super.onUnavailable()
                onUnAvailable()
            }

            // lost network connection
            override fun onLost(network: Network) {
                super.onLost(network)
                onLost()
            }
        }
        val connectivityManager = ContextCompat.getSystemService(
            context,
            ConnectivityManager::class.java
        ) as ConnectivityManager
        connectivityManager.requestNetwork(networkRequest, networkCallback)
    }
}