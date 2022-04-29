package com.example.currencyapp.framework.network

import com.example.currencyapp.data.currency.model.CurrencyListResponse
import com.example.currencyapp.data.currencydetails.model.HistoricalResponse
import com.example.currencyapp.domain.currecnydetails.model.HistoricalRequest
import com.example.currencyapp.domain.currecnydetails.model.OtherCurrencyRequest
import com.example.currencyapp.domain.currency.model.CurrencyDataRequest
import com.example.currencyapp.framework.dto.OperationResult

interface HttpApiInf {
    fun getCurrencyData(currencyDataRequest: CurrencyDataRequest): OperationResult<CurrencyListResponse>
    fun getHistoricalCurrencyData(historicalRequest: HistoricalRequest): OperationResult<HistoricalResponse>
    fun getOtherCurrencyData(currencyDataRequest: OtherCurrencyRequest): OperationResult<CurrencyListResponse>
}
