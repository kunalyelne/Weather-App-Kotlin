package com.kyodude.weatherapp.model.dataModels

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ForecastItem(
    @SerializedName("clouds")
    @Expose
    val clouds: Clouds,
    @SerializedName("dt")
    @Expose
    val dt: Int,
    @SerializedName("dt_txt")
    @Expose
    val dtTxt: String,
    @SerializedName("main")
    @Expose
    val main: MainX,
    @SerializedName("pop")
    @Expose
    val pop: Double,
    @SerializedName("rain")
    @Expose
    val rain: Rain,
    @SerializedName("sys")
    @Expose
    val sys: SysX,
    @SerializedName("visibility")
    @Expose
    val visibility: Int,
    @SerializedName("weather")
    @Expose
    val weather: List<Weather>,
    @SerializedName("wind")
    @Expose
    val wind: WindX
)