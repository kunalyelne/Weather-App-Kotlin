package com.kyodude.weatherapp.model.dataModels

import com.google.gson.annotations.SerializedName

data class Rain(
    @SerializedName("3h")
    val h: Double
)