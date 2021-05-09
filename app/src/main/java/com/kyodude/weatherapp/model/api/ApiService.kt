package com.kyodude.weatherapp.model.api

import com.kyodude.weatherapp.model.dataModels.ForecastResponse
import com.kyodude.weatherapp.model.dataModels.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("weather")
    suspend fun getCurrentWeather(@Query("q") city: String, @Query("APPID") apiKey :String, @Query("units") units : String) : Response<WeatherResponse>

    @GET("forecast")
    suspend fun getWeatherForecast(@Query("q") city: String, @Query("APPID") apiKey :String, @Query("units") units : String) : Response<ForecastResponse>
}