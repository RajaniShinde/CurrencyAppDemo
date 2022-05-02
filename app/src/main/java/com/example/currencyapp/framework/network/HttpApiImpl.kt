package com.example.currencyapp.framework.network

import com.example.currencyapp.data.currency.model.CurrencyListResponse
import com.example.currencyapp.data.currencydetails.model.HistoricalResponse
import com.example.currencyapp.domain.currecnydetails.model.HistoricalRequest
import com.example.currencyapp.domain.currecnydetails.model.OtherCurrencyRequest
import com.example.currencyapp.domain.currency.model.CurrencyDataRequest
import com.example.currencyapp.framework.dto.Error
import com.example.currencyapp.framework.dto.OperationResult
import com.example.currencyapp.framework.dto.ResponseError
import com.example.currencyapp.framework.model.BaseRequest
import com.example.currencyapp.framework.model.BlankBody
import com.google.gson.Gson
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

class HttpApiImpl @Inject constructor(
    private val apiInterfaceDao: ApiInterfaceDao

    ): HttpApiInf {
    override fun getCurrencyData(currencyDataRequest: CurrencyDataRequest): OperationResult<CurrencyListResponse> {
        return makeRetrofitRequest(
            BlankBody(),
            {
                apiInterfaceDao.getCurrencyData(currencyDataRequest.baseCurrency).execute()
            },
            ::handleResponseError
        )
    }

    override fun getHistoricalCurrencyData(historicalRequest: HistoricalRequest): OperationResult<HistoricalResponse> {
        return makeRetrofitRequest(
            BlankBody(),
            {
                apiInterfaceDao.getCurrencyRatesInTimeFrame(historicalRequest.start_date,historicalRequest.end_date).execute()
            },
            ::handleResponseError
        )
    }

    override fun getOtherCurrencyData(currencyDataRequest: OtherCurrencyRequest): OperationResult<CurrencyListResponse> {
        return makeRetrofitRequest(
            BlankBody(),
            {
                apiInterfaceDao.getSpecificExchangeRate(currencyDataRequest.baseCurrency, currencyDataRequest.symbols).execute()
            },
            ::handleResponseError
        )
    }

    private fun<RQ : BaseRequest, RS> makeRetrofitRequest(
        rq: RQ,
        retrofitRequest : (rq:RQ) -> Response<RS>,
        handleError:(Response<RS>) -> OperationResult.ErrorOperationResult): OperationResult<RS>
    {
          return try {
              val response = retrofitRequest(rq)
              if (response.isSuccessful) {
                  response.body()?.let {
                      return OperationResult.SuccessOperationResult(it)
                  } ?: kotlin.run {
                      return OperationResult.SuccessOperationResult (Response.success("true") as RS )
                  }
              } else {
                  return handleError(response)
              }
          }catch (e : Exception){
              OperationResult.ErrorOperationResult(Error.getError(e))

          }
    }

    private fun <RS> handleResponseError(
        response: Response<RS>
    ):OperationResult.ErrorOperationResult{
        val errorOperationResult = OperationResult.ErrorOperationResult(Error.getError(response.code()))
         response.errorBody()?.let {
             errorOperationResult.error.responseError = Gson().fromJson(it.string(), ResponseError::class.java)
         }
        return  errorOperationResult
    }


}