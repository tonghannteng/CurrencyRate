package com.tonghann.currencyrate.model

import com.google.gson.annotations.SerializedName

data class CurrencyResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("base")
    val base: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("rates")
    val rates: HashMap<String, Double>
)
