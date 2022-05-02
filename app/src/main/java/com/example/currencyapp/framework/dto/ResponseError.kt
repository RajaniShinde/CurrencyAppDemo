package com.example.currencyapp.framework.dto

class ResponseError {

    private var message : String? = null

    fun getMessage() : String{
        return message?: ""
    }

}