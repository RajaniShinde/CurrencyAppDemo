package com.example.currencyapp.framework.usecase

import com.example.currencyapp.framework.dto.OperationResult
import com.example.currencyapp.framework.manager.CoroutineManger
import com.example.currencyapp.framework.mapper.DomainMapper

abstract class UseCase : CoroutineManger() {

    fun<Domain , Data> OperationResult<Data>.transform(
        mapper: DomainMapper<Data , Domain>
    ): OperationResult<Domain>{
        return when (this){
            is OperationResult.Loading -> {
                this
            }
            is OperationResult.SuccessOperationResult -> {
                this.copy(mapper(this.result))
            }

            is OperationResult.ErrorOperationResult -> {
                this
            }
        }
    }
}