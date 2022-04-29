package com.example.currencyapp.framework.manager

import com.example.currencyapp.framework.dto.OperationResult
import com.example.currencyapp.framework.dto.Outcome
import com.example.currencyapp.framework.model.BaseRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.reflect.KSuspendFunction0
import kotlin.reflect.KSuspendFunction1

open class CoroutineManger {

    interface PerformTask<T>{
        fun performInBackgorund()
        fun performInForeground(op : OperationResult<T>)
    }

    fun <T> executeSuspend(
        runInBackGround: KSuspendFunction0<OperationResult<T>>,
        coroutineScope: CoroutineScope
    ):Outcome<OperationResult<T>>{
        val outCome = Outcome<OperationResult<T>>(MutableStateFlow(OperationResult.Loading()))
        val performtask = object : PerformTask<T>{
            override fun performInBackgorund() {
               coroutineScope.launch (Dispatchers.Main){
                   val result = async(Dispatchers.IO) { runInBackGround()}
                   performInForeground(result.await())
               }
            }

            override fun performInForeground(op: OperationResult<T>) {
                setResult(outCome , op, coroutineScope)
            }

        }
        performtask.performInBackgorund()
        return outCome
    }

    fun <P : BaseRequest,T> executeSuspend(
        b:P,
        runInBgFun: KSuspendFunction1<P, OperationResult<T>>,
        coroutineScope: CoroutineScope
    ):Outcome<OperationResult<T>>{
        val outCome = Outcome<OperationResult<T>>(MutableStateFlow(OperationResult.Loading()))
        val performtask = object : PerformTask<T>{
            override fun performInBackgorund() {
                coroutineScope.launch (Dispatchers.Main){
                    val result = async(Dispatchers.IO) { runInBgFun(b)}
                    performInForeground(result.await())
                }
            }

            override fun performInForeground(op: OperationResult<T>) {
                setResult(outCome , op, coroutineScope)
            }

        }
        performtask.performInBackgorund()
        return outCome
    }


    fun <T> setResult(
        outcome: Outcome<OperationResult<T>>,
        opInBetween : OperationResult<T>,
        coroutineScope: CoroutineScope
    ){
        coroutineScope.launch {
            outcome.mutableStateFlow.emit(opInBetween)
        }
    }
}