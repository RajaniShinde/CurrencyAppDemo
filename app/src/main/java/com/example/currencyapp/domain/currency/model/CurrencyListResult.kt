package com.example.currencyapp.domain.currency.model

import com.google.gson.annotations.SerializedName

data class CurrencyListResult(
    @SerializedName("rates")
    val rates: Map<String, Double>?,

)
