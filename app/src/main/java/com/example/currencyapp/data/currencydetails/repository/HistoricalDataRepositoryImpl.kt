package com.example.currencyapp.data.currencydetails.repository

import com.example.currencyapp.data.currency.model.CurrencyListResponse
import com.example.currencyapp.data.currencydetails.model.HistoricalResponse
import com.example.currencyapp.domain.currecnydetails.model.HistoricalRequest
import com.example.currencyapp.domain.currecnydetails.model.OtherCurrencyRequest
import com.example.currencyapp.domain.currecnydetails.repository.HistoricalDataRepository
import com.example.currencyapp.framework.dto.OperationResult
import com.example.currencyapp.framework.network.HttpApiInf
import javax.inject.Inject

class HistoricalDataRepositoryImpl  @Inject constructor(
    private val httpApiInf: HttpApiInf,

    ): HistoricalDataRepository{

    override fun getHistoricalData(historicalRequest: HistoricalRequest): OperationResult<HistoricalResponse> {
        return httpApiInf.getHistoricalCurrencyData(historicalRequest)

    }

    override fun getOtherCurrencyData(currencyDataRequest: OtherCurrencyRequest): OperationResult<CurrencyListResponse> {
        return httpApiInf.getOtherCurrencyData(currencyDataRequest)
    }

}