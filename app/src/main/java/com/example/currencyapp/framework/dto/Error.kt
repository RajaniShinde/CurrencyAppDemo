package com.example.currencyapp.framework.dto

import com.example.currencyapp.framework.network.NoConnectivityException
import java.net.SocketTimeoutException

class Error {

    var responseError : ResponseError? = null
    var errorCode: Int = -1

    companion object{
        const val SOME_THING_WENT_WRONG = 9000
        const val NO_INTERNET_CONNECTION = 9001
        const val SOCKET_TIMEOUT_EXCEPTION = 9002

        fun getError(errorCode: Int) : Error{
            val error = Error()
            error.errorCode = errorCode
            return error
        }

        fun getError(t : Throwable) : Error{
            val error = Error()
            if(t is NoConnectivityException) {
                error.errorCode = NO_INTERNET_CONNECTION
            }
            if( t is SocketTimeoutException){
                error.errorCode = SOCKET_TIMEOUT_EXCEPTION
            }

            return error
        }
    }
}