package com.example.currencyapp.presentation.currency.viewmodel

 import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
 import com.example.currencyapp.domain.currency.model.CurrencyListResult
 import com.example.currencyapp.domain.currency.usecase.CurrencyListUseCase
import com.example.currencyapp.framework.dto.Error
import com.example.currencyapp.framework.dto.OperationResult
import com.example.currencyapp.framework.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val currencyListUseCase: CurrencyListUseCase
) : BaseViewModel() {

    val onLoadingLD: LiveData<Boolean>
    get() = onLoadingMLD
    private val onLoadingMLD = MutableLiveData<Boolean>()

    val onErrorLD: LiveData<Error>
    get()= onErrorMLD
    private val onErrorMLD = MutableLiveData<Error>()

     val onGetCurrencySuccessLD: LiveData<CurrencyListResult?>
        get() = onGetCurrencySuccessMLD
    private val onGetCurrencySuccessMLD = MutableLiveData<CurrencyListResult?>()

    fun getCurrencyData(baseCurrency: String){
        val output = currencyListUseCase.getCurrencyData(baseCurrency,viewModelScope)
        viewModelScope.launch {
            output.mutableStateFlow.collect { it.performAction(
                    :: loadingAction,
                    :: currencySuccessResponseAction,
                    :: errorResponseAction
                )
            }
        }
    }

    private fun currencySuccessResponseAction(successOperationResult: OperationResult.SuccessOperationResult<CurrencyListResult>) {
        successOperationResult.result.also { onGetCurrencySuccessMLD.value = it }
     }

    private fun loadingAction() {
       onLoadingMLD.value = false
    }


    private fun errorResponseAction(errorOperationResult: OperationResult.ErrorOperationResult) {
        onLoadingMLD.value = false
        errorOperationResult.error.also { onErrorMLD.value = it }
    }


}