package com.example.currencyapp.domain.currecnydetails.repository

import com.example.currencyapp.data.currency.model.CurrencyListResponse
import com.example.currencyapp.data.currencydetails.model.HistoricalResponse
import com.example.currencyapp.domain.currecnydetails.model.HistoricalRequest
import com.example.currencyapp.domain.currecnydetails.model.OtherCurrencyRequest
import com.example.currencyapp.framework.dto.OperationResult

interface HistoricalDataRepository {

    fun getHistoricalData(historicalRequest: HistoricalRequest): OperationResult<HistoricalResponse>
    fun getOtherCurrencyData(currencyDataRequest: OtherCurrencyRequest): OperationResult<CurrencyListResponse>

}