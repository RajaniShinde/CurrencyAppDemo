package com.example.currencyapp.data.currencydetails.model

import com.google.gson.annotations.SerializedName

data class HistoricalResponse(
    @SerializedName("base")
    val base: String,
    @SerializedName("end_date")
    val endAt: String,
    @SerializedName("rates")
    val rates: HashMap<String, HashMap<String, Double>>,
    @SerializedName("start_date")
    val startAt: String
)