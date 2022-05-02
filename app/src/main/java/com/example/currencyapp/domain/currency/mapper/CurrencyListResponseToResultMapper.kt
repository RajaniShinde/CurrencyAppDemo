package com.example.currencyapp.domain.currency.mapper

import com.example.currencyapp.data.currency.model.CurrencyListResponse
import com.example.currencyapp.domain.currency.model.CurrencyListResult
import com.example.currencyapp.framework.mapper.DomainMapper
import javax.inject.Inject

class CurrencyListResponseToResultMapper@Inject constructor(): DomainMapper<CurrencyListResponse,
        CurrencyListResult> {
    override fun invoke(currencyListResponse: CurrencyListResponse): CurrencyListResult {
        return CurrencyListResult(currencyListResponse.success,currencyListResponse.rates)
    }
}