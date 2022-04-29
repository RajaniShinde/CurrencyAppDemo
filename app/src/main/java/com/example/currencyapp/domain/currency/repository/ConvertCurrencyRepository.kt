package com.example.currencyapp.domain.currency.repository

import com.example.currencyapp.data.currency.model.CurrencyListResponse
import com.example.currencyapp.domain.currency.model.CurrencyDataRequest
import com.example.currencyapp.framework.dto.OperationResult

interface ConvertCurrencyRepository {
    fun getCurrencyData(currencyDataRequest: CurrencyDataRequest): OperationResult<CurrencyListResponse>
 }