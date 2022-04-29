package com.example.currencyapp.domain.currecnydetails.model

import com.example.currencyapp.framework.model.BaseRequest


data class HistoricalRequest (
    val baseCurrency : String,
    val start_date : String,
    val end_date : String,
 ): BaseRequest()
