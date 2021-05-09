package com.kyodude.weatherapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.kyodude.weatherapp.databinding.ActivityErrorBinding
import com.kyodude.weatherapp.viewModel.ErrorViewModel

class ErrorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityErrorBinding
    private lateinit var errorViewModel: ErrorViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityErrorBinding.inflate(layoutInflater)

        setContentView(binding.root)

        errorViewModel = ViewModelProvider(this).get(ErrorViewModel::class.java)

        binding.button.setOnClickListener {
            errorViewModel.retryPressed()
        }

        errorViewModel.isRetryPressed().observe(this, Observer { pressed ->
            if(pressed) finish()
        })

    }
}