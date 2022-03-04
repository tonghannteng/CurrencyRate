package com.tonghann.currencyrate.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tonghann.currencyrate.model.CurrencyResponse
import com.tonghann.currencyrate.repository.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val currencyRepository: CurrencyRepository) :
    ViewModel() {

    private val _currencyResponse = MutableLiveData<CurrencyResponse>()
    private val _errorState = MutableLiveData<String>()

    val currencyResponse: LiveData<CurrencyResponse>
        get() = _currencyResponse

    val errorState: LiveData<String>
        get() = _errorState

    fun getAllCurrencyResponse() {
        viewModelScope.launch {

            try {
                val response = currencyRepository.getAllCurrency()
                if (response.isSuccessful) {
                    response.body()?.let {
                        _currencyResponse.value = response.body()
                        Log.d(TAG, "success")
                    }
                } else {
                    _errorState.value = response.errorBody().toString()
                    Log.d(TAG, response.message().toString())
                    Log.d(TAG, "nonononono")
                }

            } catch (exception: Exception) {
                _errorState.value = exception.message.toString()
                Log.d(TAG, exception.message.toString())
            }

        }
    }

    companion object {
        const val TAG = "MainViewModelLog"
    }
}