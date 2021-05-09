package com.kyodude.weatherapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ErrorViewModel: ViewModel() {
    private val retryPressed : MutableLiveData<Boolean> = MutableLiveData(false)

    fun retryPressed() {
        retryPressed.value = true
    }

    fun isRetryPressed(): LiveData<Boolean> {
        return retryPressed
    }
}