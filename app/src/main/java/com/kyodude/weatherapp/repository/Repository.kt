package com.kyodude.weatherapp.repository

import com.kyodude.weatherapp.model.dataModels.ForecastResponse
import com.kyodude.weatherapp.model.dataModels.WeatherResponse
import com.kyodude.weatherapp.util.Resource

interface Repository {
    suspend fun getWeather(city:String): Resource<WeatherResponse>
    suspend fun getWeatherForecast(city:String): Resource<ForecastResponse>
}