package com.example.currencyapp.framework.viewmodel

import androidx.lifecycle.ViewModel
import com.example.currencyapp.framework.dto.OperationResult

abstract class BaseViewModel : ViewModel() {

//    fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { setValue(initialValue) }

    fun<T> OperationResult<T>.performAction(
        f1 : () -> Unit,
        f2: (OperationResult.SuccessOperationResult<T>) -> Unit,
        f3: (OperationResult.ErrorOperationResult) -> Unit
    )=
        when (this){
            is OperationResult.Loading -> {
                f1()
            }
            is OperationResult.SuccessOperationResult -> {
                f2(this)
            }
            is OperationResult.ErrorOperationResult -> {
                f3(this)
            }
        }
}