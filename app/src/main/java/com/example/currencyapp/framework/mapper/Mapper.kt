package com.example.currencyapp.framework.mapper

interface Mapper <T, R>{
    fun invoke(t: T): R
}