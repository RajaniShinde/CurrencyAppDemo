package com.example.currencyapp.framework.dto

import com.example.currencyapp.framework.dto.Error.Companion.SOME_THING_WENT_WRONG
import kotlinx.coroutines.flow.MutableStateFlow

class Outcome<R> constructor(val mutableStateFlow: MutableStateFlow<R>)

sealed class OperationResult<out T> {
    abstract fun <R> copy(r :R) : OperationResult<R>

    //for loading
    data class Loading(val isLoadingRequired: Boolean = true) : OperationResult<Nothing>() {
        override fun <R> copy(r: R): OperationResult<R> {
            return Loading()
        }
    }

    // for success result
    data class SuccessOperationResult<T>(val result: T): OperationResult<T>(){
        override fun <R> copy(r: R): OperationResult<R> {
            return SuccessOperationResult(r)
        }
    }

    //for error result
    data class ErrorOperationResult constructor(val error: Error) : OperationResult<Nothing>(){
        override fun <R> copy(r: R): OperationResult<R> {
            return ErrorOperationResult(error)
        }
    }

    fun isSuccess() : SuccessOperationResult<out T>?{
        return when (this){
            is Loading -> {
                null
            }
            is SuccessOperationResult -> {
                this
            }
            is ErrorOperationResult -> {
                null
            }
        }
    }

    fun getErrorDetails() : Error{
        val error = Error.getError(SOME_THING_WENT_WRONG)

        return when (this){
            is Loading ->{
                error
            }
            is SuccessOperationResult ->{
                error
            }
            is ErrorOperationResult ->{
                this.error
            }
        }
    }


}