package com.kyodude.weatherapp.model.dataModels

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MainX(
    @SerializedName("feels_like")
    @Expose
    val feelsLike: Double,
    @SerializedName("grnd_level")
    @Expose
    val grndLevel: Int,
    @SerializedName("humidity")
    @Expose
    val humidity: Int,
    @SerializedName("pressure")
    @Expose
    val pressure: Int,
    @SerializedName("sea_level")
    @Expose
    val seaLevel: Int,
    @SerializedName("temp")
    @Expose
    val temp: Double,
    @SerializedName("temp_kf")
    @Expose
    val tempKf: Double,
    @SerializedName("temp_max")
    @Expose
    val tempMax: Double,
    @SerializedName("temp_min")
    @Expose
    val tempMin: Double
)