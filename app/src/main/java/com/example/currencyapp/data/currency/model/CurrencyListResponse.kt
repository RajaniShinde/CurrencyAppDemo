package com.example.currencyapp.data.currency.model

import com.google.gson.annotations.SerializedName

data class CurrencyListResponse(
    val success: Boolean,
    @SerializedName("rates")
    val rates: Map<String, Double>?,
    @SerializedName("base")
    val base: String,
    @SerializedName("date")
    val date: String
)
