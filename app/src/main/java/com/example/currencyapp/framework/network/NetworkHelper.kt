package com.example.currencyapp.framework.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkHelper @Inject constructor(@ApplicationContext private val context: Context){

    @Suppress("DEPRECATION")
     fun isNetworkConnected(): Boolean{
        val result : Boolean
        val connectivityManger = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            connectivityManger.activeNetwork ?: return false
        }else{
            val networkInfo = connectivityManger.activeNetworkInfo
            return  networkInfo != null && networkInfo.isConnected
        }

        val activeNetwork = connectivityManger.getNetworkCapabilities(networkCapabilities) ?: return false

        result = when{
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true

            else -> false
        }

        return result

    }
}