package com.example.currencyapp.domain.currecnydetails.mapper

import com.example.currencyapp.data.currencydetails.model.HistoricalResponse
import com.example.currencyapp.domain.currecnydetails.model.HistoricalResult
import com.example.currencyapp.framework.mapper.DomainMapper
import javax.inject.Inject

class HistoricalCurrencyResponseToResultmapper @Inject constructor(): DomainMapper<HistoricalResponse,
        HistoricalResult> {
    override fun invoke(historicalResponse: HistoricalResponse): HistoricalResult {
        return HistoricalResult(historicalResponse.rates)
    }
}