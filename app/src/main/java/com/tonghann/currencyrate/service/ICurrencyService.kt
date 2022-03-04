package com.tonghann.currencyrate.service

import com.tonghann.currencyrate.model.CurrencyResponse
import retrofit2.Response
import retrofit2.http.GET

interface ICurrencyService {

    @GET("api/latest?access_key=7a79e25c0fce9d59b1b4e258e636e3ee")
    suspend fun getAllRates(): Response<CurrencyResponse>
}