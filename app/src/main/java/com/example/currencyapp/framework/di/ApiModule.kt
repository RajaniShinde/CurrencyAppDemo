package com.example.currencyapp.framework.di

import android.content.Context
import com.example.currencyapp.framework.common.NETWORK_TIMEOUT
import com.example.currencyapp.framework.network.*
import com.example.currencyapp.framework.ui.App
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    fun provideApplication(@ApplicationContext app: Context): App{
        return app as App
    }

    @Provides
    @Singleton
    fun provideGson(): Gson{
        return GsonBuilder()
            .setLenient()
            .create()
    }

    @Provides
    @Singleton
    fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        context: Context,
        networkHelper: NetworkHelper
    )=
        OkHttpClient.Builder()
        .readTimeout(NETWORK_TIMEOUT,TimeUnit.SECONDS)
        .writeTimeout(NETWORK_TIMEOUT,TimeUnit.SECONDS)
        .connectTimeout(NETWORK_TIMEOUT,TimeUnit.SECONDS)
        .addInterceptor(NetworkConnectionInterceptor(context,networkHelper))
        .addInterceptor(httpLoggingInterceptor)
        .build()

    @Provides
    fun provideRetrofit(gson: Gson,okHttpClient: OkHttpClient): Retrofit{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("https://api.exchangerate.host/")
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideHttpApiService(retrofit: Retrofit):ApiInterfaceDao{
        return retrofit.create(ApiInterfaceDao::class.java)
    }

    @Provides
    @Singleton
    fun provideHttpApiHelper(httpApiImpl: HttpApiImpl): HttpApiInf = httpApiImpl

    @Provides
    @Singleton
    fun providesContext(@ApplicationContext context : Context): Context{
        return context.applicationContext
    }
}