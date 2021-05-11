package com.kyodude.weatherapp.viewModel

import android.util.Log
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import androidx.lifecycle.*
import com.kyodude.weatherapp.model.dataModels.ForecastItem
import com.kyodude.weatherapp.model.dataModels.ForecastListItem
import com.kyodude.weatherapp.repository.MainRepository
import com.kyodude.weatherapp.util.DispatcherProvider
import com.kyodude.weatherapp.util.Extensions.getAvgTemp
import com.kyodude.weatherapp.util.Extensions.getOffset
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

    val TAG = MainViewModel::class.java.simpleName

    data class HomeData(
            var temp: String = "",
            var forecast: List<ForecastListItem> = ArrayList()
    )

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
                                    var index = forecastResponse.data.list.getOffset()
                                    while (index< forecastResponse.data.cnt)
                                    {
                                        try {
                                            val temp = if (index+8<forecastResponse.data.cnt)
                                                forecastResponse.data.list.subList(index,index+8).getAvgTemp()
                                            else
                                                forecastResponse.data.list.subList(index,index+(forecastResponse.data.cnt-index)).getAvgTemp()

                                            val item = ForecastListItem(forecastResponse.data.list.get(index).dtTxt, temp.toInt())
                                            list.add(item)
                                            index+=8
                                        }
                                        catch (e:ArithmeticException) {
                                            e.printStackTrace()
                                            Log.e(TAG,"Divide by zero")
                                        }
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

    fun setCity(city: String) {
        if (city.equals(null)|| city.equals(""))
            _dataFlow.value = HomeScreenEvent.Failure("Not a valid city name")
        else
            getTemperature(city)
    }

    fun getRotateAnimation(): RotateAnimation{
        val rotateAnimation = RotateAnimation(
                0f, 359f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f

        )
        rotateAnimation.duration = 1500
        rotateAnimation.interpolator = LinearInterpolator()
        rotateAnimation.repeatCount = RotateAnimation.INFINITE
        return rotateAnimation
    }
}