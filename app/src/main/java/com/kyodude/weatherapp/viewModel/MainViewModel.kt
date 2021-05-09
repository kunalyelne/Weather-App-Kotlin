package com.kyodude.weatherapp.viewModel

import androidx.lifecycle.*
import com.kyodude.weatherapp.model.dataModels.ForecastItem
import com.kyodude.weatherapp.model.dataModels.ForecastListItem
import com.kyodude.weatherapp.repository.MainRepository
import com.kyodude.weatherapp.util.DispatcherProvider
import com.kyodude.weatherapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val dispatchers: DispatcherProvider
): ViewModel() {
    val TAG = "MainActivity"

    inner class HomeData {
        var temp: String = ""
        var forecast: List<ForecastListItem> = ArrayList()
    }

    sealed class HomeScreenEvent {
        class Success(val data: HomeData): HomeScreenEvent()
        class Failure(val errorText: String): HomeScreenEvent()
        object Loading : HomeScreenEvent()
        object Empty : HomeScreenEvent()
    }

    private val _dataFlow = MutableStateFlow<HomeScreenEvent> (HomeScreenEvent.Empty)
    val dataFlow: StateFlow<HomeScreenEvent> = _dataFlow

    private fun getTemperature(city: String) {
        viewModelScope.launch(dispatchers.io) {
            _dataFlow.value = HomeScreenEvent.Loading
            when(val weatherResponse = mainRepository.getWeather(city)) {
                is Resource.Error -> _dataFlow.value = HomeScreenEvent.Failure(weatherResponse.message!!)
                is Resource.Success -> {
                    val temp = weatherResponse.data?.main?.temp
                    if(temp == null) {
                        _dataFlow.value = HomeScreenEvent.Failure("Unexpected error in weather call")
                    } else {
                        val data = HomeData()
                        data.temp = "${temp.toInt()}°"
                        when(val forecastResponse = mainRepository.getWeatherForecast(city)) {
                            is Resource.Error -> _dataFlow.value = HomeScreenEvent.Failure(forecastResponse.message!!)
                            is Resource.Success -> {
                                val list = ArrayList<ForecastListItem>()
                                if(forecastResponse.data?.list == null) {
                                    _dataFlow.value = HomeScreenEvent.Failure("Unexpected error in weather call")
                                } else {
                                    var index = getOffset(forecastResponse.data.list)
                                    while (index< forecastResponse.data.cnt)
                                    {
                                        val temp = if (index+8<forecastResponse.data.cnt)
                                            getAvgTemp(forecastResponse.data.list.subList(index,index+8))
                                        else
                                            getAvgTemp(forecastResponse.data.list.subList(index,index+(forecastResponse.data.cnt-index)))

                                        val item = ForecastListItem(forecastResponse.data.list.get(index).dtTxt, temp.toInt())
                                        list.add(item)
                                        index+=8
                                    }
                                    data.forecast = list
                                    _dataFlow.value = HomeScreenEvent.Success(
                                            data
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getAvgTemp(list: List<ForecastItem>): Double {
        var sum = 0.0
        for (i in list) {
            sum+=i.main.temp
        }
        return sum/list.size
    }

    private fun getOffset(list: List<ForecastItem>): Int {
        var found = false
        var i = 0
        while (!found) {
            val x = list[i].dtTxt.split(" ")[1].split(":")[0].toInt()
            if(x==0) found = true
            else i++
        }
        return i
    }

    fun setCity(city: String) {
        getTemperature(city)
    }
}