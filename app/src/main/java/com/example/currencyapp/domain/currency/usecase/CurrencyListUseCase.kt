package com.example.currencyapp.domain.currency.usecase

import com.example.currencyapp.data.currency.model.CurrencyListResponse
import com.example.currencyapp.domain.currency.model.CurrencyDataRequest
import com.example.currencyapp.domain.currency.model.CurrencyListResult
import com.example.currencyapp.domain.currency.repository.ConvertCurrencyRepository
import com.example.currencyapp.framework.dto.OperationResult
import com.example.currencyapp.framework.dto.Outcome
import com.example.currencyapp.framework.mapper.DomainMapper
import com.example.currencyapp.framework.usecase.UseCase
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class CurrencyListUseCase @Inject constructor(
    private val convertCurrencyRepository: ConvertCurrencyRepository,
    private val responseToDomain : DomainMapper<CurrencyListResponse, CurrencyListResult>
): UseCase() {

    fun getCurrencyData(
        baseCurrency: String,
        coroutineScope: CoroutineScope
    ): Outcome<OperationResult<CurrencyListResult>> {
        return executeSuspend(
            CurrencyDataRequest(baseCurrency),
            ::getCurrencyDataRequest,
            coroutineScope
        )
    }

    private suspend fun getCurrencyDataRequest(currencyDataRequest:CurrencyDataRequest)
            : OperationResult<CurrencyListResult> {
        return convertCurrencyRepository.getCurrencyData(currencyDataRequest)
            .transform(responseToDomain)
    }
}
