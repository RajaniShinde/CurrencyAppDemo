package com.example.currencyapp.domain.currecnydetails.usecase

import com.example.currencyapp.data.currencydetails.model.HistoricalResponse
import com.example.currencyapp.domain.currecnydetails.model.HistoricalRequest
import com.example.currencyapp.domain.currecnydetails.model.HistoricalResult
import com.example.currencyapp.domain.currecnydetails.repository.HistoricalDataRepository
import com.example.currencyapp.framework.dto.OperationResult
import com.example.currencyapp.framework.dto.Outcome
import com.example.currencyapp.framework.mapper.DomainMapper
import com.example.currencyapp.framework.usecase.UseCase
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class HistoricalCurrencyUseCase  @Inject constructor(
    private val historicalDataRepository: HistoricalDataRepository,
    private val responseToDomain : DomainMapper<HistoricalResponse, HistoricalResult>
): UseCase() {

    fun getHistoricalCurrencyData(
        baseCurrency: String,
        start_date: String,
        end_date: String,
        coroutineScope: CoroutineScope
    ): Outcome<OperationResult<HistoricalResult>> {
        return executeSuspend(
            HistoricalRequest(baseCurrency,start_date,end_date),
            ::getCurrencyDataRequest,
            coroutineScope
        )
    }

    private suspend fun getCurrencyDataRequest(historicalRequest: HistoricalRequest)
            : OperationResult<HistoricalResult> {
        return historicalDataRepository.getHistoricalData(historicalRequest)
            .transform(responseToDomain)
    }
}
