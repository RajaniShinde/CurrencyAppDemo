package com.example.currencyapp.domain.currecnydetails.model

import com.google.gson.annotations.SerializedName

data class HistoricalResult(
    @SerializedName("rates")
    val rates: HashMap<String, HashMap<String, Double>>
)