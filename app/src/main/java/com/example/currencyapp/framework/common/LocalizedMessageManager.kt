package com.example.currencyapp.framework.common

import android.content.Context
import com.example.currencyapp.R
import javax.inject.Inject

class LocalizedMessageManager @Inject constructor(private val context: Context) {

    fun getNoInternetMsg() :String = context.getString(R.string.no_internet_connection)
    fun getUnknownErrorMessage(): String = context.getString(R.string.unknown_error_msg)
    fun getServerNotRespondingMsg(): String = context.getString(R.string.server_not_responding_msg)
}