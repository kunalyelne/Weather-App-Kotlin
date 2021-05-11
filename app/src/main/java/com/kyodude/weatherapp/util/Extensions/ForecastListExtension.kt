package com.kyodude.weatherapp.util.Extensions

import com.kyodude.weatherapp.model.dataModels.ForecastItem

fun List<ForecastItem>.getOffset():Int {
    var i = 0
    for( item in this) {
        val x = item.dtTxt.split(" ")[1].split(":")[0].toInt()
        if (x==0) return i
        else i++
    }
    return i
}

fun List<ForecastItem>.getAvgTemp(): Double {
    if(this.size==0) throw ArithmeticException()
    var sum = 0.0
    for (i in this) {
        sum+=i.main.temp
    }
    return sum/this.size
}