package com.example.currencyapp.domain.currecnydetails.usecase

import com.example.currencyapp.data.currency.model.CurrencyListResponse
import com.example.currencyapp.domain.currecnydetails.model.OtherCurrencyRequest
import com.example.currencyapp.domain.currecnydetails.repository.HistoricalDataRepository
import com.example.currencyapp.domain.currency.model.CurrencyListResult
import com.example.currencyapp.framework.dto.OperationResult
import com.example.currencyapp.framework.dto.Outcome
import com.example.currencyapp.framework.mapper.DomainMapper
import com.example.currencyapp.framework.usecase.UseCase
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class OtherCurrencyUseCase @Inject constructor(
    private val historicalDataRepository: HistoricalDataRepository,
    private val responseToDomain : DomainMapper<CurrencyListResponse, CurrencyListResult>
): UseCase() {

    fun getCurrencyData(
        baseCurrency: String,
        symbols: String,
        coroutineScope: CoroutineScope
    ): Outcome<OperationResult<CurrencyListResult>> {
        return executeSuspend(
            OtherCurrencyRequest(baseCurrency,symbols),
            ::getCurrencyDataRequest,
            coroutineScope
        )
    }

    private suspend fun getCurrencyDataRequest(currencyDataRequest: OtherCurrencyRequest)
            : OperationResult<CurrencyListResult> {
        return historicalDataRepository.getOtherCurrencyData(currencyDataRequest)
            .transform(responseToDomain)
    }
}