package com.example.currencyapp.framework.network

import com.example.currencyapp.data.currency.model.CurrencyListResponse
import com.example.currencyapp.data.currencydetails.model.HistoricalResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiInterfaceDao {

    @GET("latest?")
     fun getCurrencyData(
        @Query("base") base: String

        ): Call<CurrencyListResponse>


    @GET("timeseries")
    fun getCurrencyRatesInTimeFrame(
         @Query("start_date") start_date: String,
         @Query("end_date") end_date: String,
        ): Call<HistoricalResponse>

    @GET("latest")
    fun getSpecificExchangeRate(
        @Query("base") base: String,
        @Query("symbols") symbol: String,
    ): Call<CurrencyListResponse>
}