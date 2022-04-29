package com.example.currencyapp.framework.errorhandler

import com.example.currencyapp.framework.common.LocalizedMessageManager
import com.example.currencyapp.framework.dto.Error

class ErrorDetails(private val error:Error , private val localizedMessageManager: LocalizedMessageManager) {

    fun getErrorMessage() : String{
        error.responseError?.getMessage()?.let {
            return it
        }

        return when(error.errorCode){
            Error.NO_INTERNET_CONNECTION -> {
                localizedMessageManager.getNoInternetMsg()
            }
            Error.SOCKET_TIMEOUT_EXCEPTION -> {
                localizedMessageManager.getServerNotRespondingMsg()
            }
           else ->{
               localizedMessageManager.getUnknownErrorMessage()
           }
        }
    }

}