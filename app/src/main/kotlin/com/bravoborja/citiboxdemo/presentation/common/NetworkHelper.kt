package com.bravoborja.citiboxdemo.presentation.common

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities.*
import android.net.NetworkRequest
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object NetworkHelper {

    private val networkLiveData: MutableLiveData<Boolean> = MutableLiveData()

    fun getNetworkLiveData(context: Context): LiveData<Boolean> {
        var isConnected = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network?) {
                networkLiveData.postValue(true)
            }

            override fun onLost(network: Network?) {
                networkLiveData.postValue(false)
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback)
        } else {
            val builder = NetworkRequest.Builder()
            connectivityManager.registerNetworkCallback(builder.build(), networkCallback)
        }

        isConnected = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
            networkCapabilities?.let {
                when {
                    networkCapabilities.hasTransport(TRANSPORT_WIFI) ||
                            networkCapabilities.hasTransport(TRANSPORT_CELLULAR) ||
                            networkCapabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            } ?: false
        } else {
            val activeNetwork = connectivityManager.activeNetworkInfo
            activeNetwork?.isConnected == true
        }

        networkLiveData.postValue(isConnected)

        return networkLiveData
    }
}
