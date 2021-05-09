package com.kyodude.weatherapp.model.dataModels

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SysX(
    @SerializedName("pod")
    @Expose
    val pod: String
)