package com.example.currencyapp.framework.di

import com.example.currencyapp.data.currency.repository.ConvertCurrencyRepositoryImpl
import com.example.currencyapp.data.currencydetails.repository.HistoricalDataRepositoryImpl
import com.example.currencyapp.domain.currecnydetails.mapper.HistoricalCurrencyResponseToResultmapper
import com.example.currencyapp.domain.currecnydetails.repository.HistoricalDataRepository
import com.example.currencyapp.domain.currecnydetails.usecase.HistoricalCurrencyUseCase
import com.example.currencyapp.domain.currecnydetails.usecase.OtherCurrencyUseCase
import com.example.currencyapp.domain.currency.mapper.CurrecnyListResponseToResultMapper
import com.example.currencyapp.domain.currency.repository.ConvertCurrencyRepository
import com.example.currencyapp.domain.currency.usecase.CurrencyListUseCase
import com.example.currencyapp.framework.network.HttpApiInf
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent


@Module
@InstallIn(ActivityRetainedComponent::class)
class CurrecnyUseCaseModule {

    @Provides
    fun providesCurrencyRepository(httpApiInf: HttpApiInf): ConvertCurrencyRepository{
        return ConvertCurrencyRepositoryImpl(httpApiInf)
    }

    @Provides
    fun providesHistoricalDataRepository(httpApiInf: HttpApiInf): HistoricalDataRepository{
        return HistoricalDataRepositoryImpl(httpApiInf)
    }

    @Provides
    fun providesCurrencyResponseToMapper():HistoricalCurrencyResponseToResultmapper{
        return HistoricalCurrencyResponseToResultmapper()
    }

    @Provides
    fun provideHistoricalCurrencyUseCase(
        convertCurrencyRepository:HistoricalDataRepository,
        responseToDomain: HistoricalCurrencyResponseToResultmapper
    ) : HistoricalCurrencyUseCase{
        return HistoricalCurrencyUseCase(convertCurrencyRepository,responseToDomain)
    }

    @Provides
    fun providesGetCurrencyResponseToMapper():CurrecnyListResponseToResultMapper{
        return CurrecnyListResponseToResultMapper()
    }

    @Provides
    fun provideListCurrencyUseCase(
        convertCurrencyRepository:ConvertCurrencyRepository,
        responseToDomain: CurrecnyListResponseToResultMapper
    ) : CurrencyListUseCase{
        return CurrencyListUseCase(convertCurrencyRepository,responseToDomain)
    }

    @Provides
    fun provideListOtherCurrencyUseCase(
        convertCurrencyRepository:HistoricalDataRepository,
        responseToDomain: CurrecnyListResponseToResultMapper
    ) : OtherCurrencyUseCase{
        return OtherCurrencyUseCase(convertCurrencyRepository,responseToDomain)
    }
}