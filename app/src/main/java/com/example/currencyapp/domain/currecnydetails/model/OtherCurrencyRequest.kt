package com.example.currencyapp.domain.currecnydetails.model

import com.example.currencyapp.framework.model.BaseRequest

data class OtherCurrencyRequest (
    val baseCurrency : String,
    val symbols : String,
): BaseRequest()