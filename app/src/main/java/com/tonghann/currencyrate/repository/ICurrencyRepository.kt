package com.tonghann.currencyrate.repository

import com.tonghann.currencyrate.model.CurrencyResponse
import retrofit2.Response

interface ICurrencyRepository {

    suspend fun getAllCurrency(): Response<CurrencyResponse>
}