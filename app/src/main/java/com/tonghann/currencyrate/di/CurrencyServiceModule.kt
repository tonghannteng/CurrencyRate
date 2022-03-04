package com.tonghann.currencyrate.di

import com.tonghann.currencyrate.repository.CurrencyRepository
import com.tonghann.currencyrate.repository.ICurrencyRepository
import com.tonghann.currencyrate.service.ICurrencyService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CurrencyServiceModule {

    @Provides
    @Singleton
    fun provideRetrofit(): ICurrencyService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(ICurrencyService::class.java)

    @Provides
    @Singleton
    fun provideRepository(currencyService: ICurrencyService) =
        CurrencyRepository(currencyService) as ICurrencyRepository

    companion object {
        const val BASE_URL = "http://data.fixer.io/"
    }
}