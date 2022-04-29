package com.example.currencyapp.presentation.currencydetail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.currencyapp.domain.currecnydetails.model.HistoricalResult
import com.example.currencyapp.domain.currecnydetails.usecase.HistoricalCurrencyUseCase
import com.example.currencyapp.domain.currecnydetails.usecase.OtherCurrencyUseCase
import com.example.currencyapp.domain.currency.model.CurrencyListResult
import com.example.currencyapp.framework.dto.Error
import com.example.currencyapp.framework.dto.OperationResult
import com.example.currencyapp.framework.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CurrencyDetailViewModel  @Inject constructor(
    private val historicalCurrencyUseCase: HistoricalCurrencyUseCase,
    private val currencyListUseCase: OtherCurrencyUseCase


) : BaseViewModel()
{
    private val latestRatesLiveData: MutableLiveData<List<Pair<String, Double>>> = MutableLiveData<List<Pair<String, Double>>>()

    val onLoadingLD: LiveData<Boolean>
        get() = onLoadingMLD
    private val onLoadingMLD = MutableLiveData<Boolean>()

    val onErrorLD: LiveData<Error>
        get()= onErrorMLD
    private val onErrorMLD = MutableLiveData<Error>()

    val onSuccessLD: LiveData<HistoricalResult?>
        get() = onSuccessMLD
    private val onSuccessMLD = MutableLiveData<HistoricalResult?>()

    val onGetCurrencySuccessLD: LiveData<CurrencyListResult?>
        get() = onGetCurrencySuccessMLD
    private val onGetCurrencySuccessMLD = MutableLiveData<CurrencyListResult?>()


    fun getHistoricalCurrencyData(
        baseCurrecny: String,
        startDate: String,
        endDate : String,
    ){
        val output = historicalCurrencyUseCase.getHistoricalCurrencyData(baseCurrecny,startDate, endDate, viewModelScope)
        viewModelScope.launch {
            output.mutableStateFlow.collect {
                it.performAction(
                    :: loadingAction,
                    :: successResponseAction,
                    :: errorResponseAction
                )
            }
        }
    }

    private fun loadingAction() {
        onLoadingMLD.value = false
    }

    private fun successResponseAction(successOperationResult: OperationResult.SuccessOperationResult<HistoricalResult>) {
        onLoadingMLD.value = false
        onSuccessMLD.value = successOperationResult.result

    }
    private fun errorResponseAction(errorOperationResult: OperationResult.ErrorOperationResult) {
        onLoadingMLD.value = false
        errorOperationResult.error.also { onErrorMLD.value = it }
    }

    fun getOtherCurrencyData(baseCurrency: String, symbols:String){
        val output = currencyListUseCase.getCurrencyData(baseCurrency,symbols,viewModelScope)
        viewModelScope.launch {
            output.mutableStateFlow.collect {
                it.performAction(
                    :: loadingAction,
                    :: currencySuccessResponseAction,
                    :: errorResponseAction
                )
            }
        }
    }

    private fun currencySuccessResponseAction(successOperationResult: OperationResult.SuccessOperationResult<CurrencyListResult>) {
        successOperationResult.result.also { onGetCurrencySuccessMLD.value = it }
        latestRatesLiveData.value = successOperationResult.result.rates?.toList()
    }

    fun getLatestRates(): LiveData<List<Pair<String, Double>>> {
        return latestRatesLiveData
    }

}