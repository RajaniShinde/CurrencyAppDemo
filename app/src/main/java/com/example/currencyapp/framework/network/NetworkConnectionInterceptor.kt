package com.example.currencyapp.framework.network

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class NetworkConnectionInterceptor @Inject constructor(val context : Context, private val networkHelper : NetworkHelper
) : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        if(!networkHelper.isNetworkConnected()){
            throw NoConnectivityException()
        }

        val builder : Request.Builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }

}

class NoConnectivityException : IOException(){
    override val message : String
       get() = "No Internet Connection"
}