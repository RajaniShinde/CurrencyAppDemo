package com.example.currencyapp.data.currency.repository

import com.example.currencyapp.data.currency.model.CurrencyListResponse
import com.example.currencyapp.domain.currency.model.CurrencyDataRequest
import com.example.currencyapp.domain.currency.repository.ConvertCurrencyRepository
import com.example.currencyapp.framework.dto.OperationResult
import com.example.currencyapp.framework.network.HttpApiInf
import javax.inject.Inject

class ConvertCurrencyRepositoryImpl @Inject constructor(
    private val httpApiInf: HttpApiInf,

): ConvertCurrencyRepository {
    override fun getCurrencyData(currencyDataRequest: CurrencyDataRequest): OperationResult<CurrencyListResponse> {
       return httpApiInf.getCurrencyData(currencyDataRequest)
    }


}