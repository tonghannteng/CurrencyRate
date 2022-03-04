package com.tonghann.currencyrate.repository

import com.tonghann.currencyrate.model.CurrencyResponse
import com.tonghann.currencyrate.service.ICurrencyService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class CurrencyRepository @Inject constructor(private val currencyService: ICurrencyService) :
    ICurrencyRepository {

    override suspend fun getAllCurrency(): Response<CurrencyResponse> {
        return withContext(Dispatchers.IO) {
            currencyService.getAllRates()
        }
    }
}