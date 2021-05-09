package com.kyodude.weatherapp.di

import com.kyodude.weatherapp.model.api.ApiService
import com.kyodude.weatherapp.model.api.Config
import com.kyodude.weatherapp.repository.MainRepository
import com.kyodude.weatherapp.repository.Repository
import com.kyodude.weatherapp.util.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SingletonModule {

    @Singleton
    @Provides
    fun provideApiService(): ApiService = Retrofit.Builder()
        .baseUrl(Config.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideMainRepsoitory(api: ApiService, apiKey: String, units: String) : Repository = MainRepository(api, apiKey, units)

    @Singleton
    @Provides
    fun provideDispatcher(): DispatcherProvider = object: DispatcherProvider{
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
        override val default: CoroutineDispatcher
            get() = Dispatchers.Default
        override val unconfined: CoroutineDispatcher
            get() = Dispatchers.Unconfined
    }

    @Singleton
    @Provides
    @Named("apiKey")
    fun provideApiKey(): String = Config.apiKey

    @Singleton
    @Provides
    @Named("units")
    fun provideUnits(): String = Config.units

}