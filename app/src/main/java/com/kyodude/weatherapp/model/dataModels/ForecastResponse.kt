package com.kyodude.weatherapp.model.dataModels

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ForecastResponse(
    @SerializedName("city")
    @Expose
    val city: City,
    @SerializedName("cnt")
    @Expose
    val cnt: Int,
    @SerializedName("cod")
    @Expose
    val cod: String,
    @SerializedName("list")
    @Expose
    val list: List<ForecastItem>,
    @SerializedName("message")
    @Expose
    val message: Int
)