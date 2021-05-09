package com.kyodude.weatherapp.repository

import com.kyodude.weatherapp.model.api.ApiService
import com.kyodude.weatherapp.model.dataModels.ForecastResponse
import com.kyodude.weatherapp.model.dataModels.WeatherResponse
import com.kyodude.weatherapp.util.Resource
import javax.inject.Inject
import javax.inject.Named

class MainRepository @Inject constructor(
    private val api: ApiService,
    @Named("apiKey") private val apiKey: String,
    @Named("units") private val units: String
) : Repository {

    override suspend fun getWeather(city: String): Resource<WeatherResponse> {
        return try {
            val response = api.getCurrentWeather(city, apiKey, units)
            val result = response.body()
            if(response.isSuccessful && result != null) {
                Resource.Success(result)
            }
            else {
                Resource.Error(response.message())
            }
        }
        catch (e: Exception){
            e.stackTrace
            Resource.Error("An error occured while fetching current weather.")
        }
    }

    override suspend fun getWeatherForecast(city: String): Resource<ForecastResponse> {
        return try {
            val response = api.getWeatherForecast(city, apiKey, units)
            val result = response.body()
            if(response.isSuccessful && result != null) {
                Resource.Success(result)
            }
            else {
                Resource.Error(response.message())
            }
        }
        catch (e: Exception){
            e.stackTrace
            Resource.Error(e.message ?: "An error occured while fetching weather forecast.")
        }
    }
}