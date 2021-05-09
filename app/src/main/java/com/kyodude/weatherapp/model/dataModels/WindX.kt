package com.kyodude.weatherapp.model.dataModels

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WindX(
    @SerializedName("deg")
    @Expose
    val deg: Int,
    @SerializedName("gust")
    @Expose
    val gust: Double,
    @SerializedName("speed")
    @Expose
    val speed: Double
)