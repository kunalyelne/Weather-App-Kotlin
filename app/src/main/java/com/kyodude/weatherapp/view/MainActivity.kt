package com.kyodude.weatherapp.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.kyodude.weatherapp.databinding.ActivityMainBinding
import com.kyodude.weatherapp.model.api.Config
import com.kyodude.weatherapp.util.dp2px
import com.kyodude.weatherapp.util.screenRectPx
import com.kyodude.weatherapp.view.adapter.ForecastListAdapter
import com.kyodude.weatherapp.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private lateinit var adapter: ForecastListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.include.forecastList.layoutManager = LinearLayoutManager(this)
        adapter = ForecastListAdapter()
        val height = screenRectPx.height() - binding.currentTemp.height - binding.cityName.height - 320.dp2px
        val params: ViewGroup.LayoutParams = binding.include.forecastList.layoutParams
        params.height = height
        binding.include.forecastList.layoutParams = params
        binding.include.forecastList.adapter = adapter

        bottomSheetBehavior = BottomSheetBehavior.from(binding.include.bottomSheet)
        bottomSheetBehavior.isDraggable = false
    }

    override fun onResume() {
        super.onResume()

        binding.loader.startAnimation(mainViewModel.getRotateAnimation())
        mainViewModel.setCity(Config.city)

        lifecycleScope.launchWhenStarted {
            mainViewModel.dataFlow.collect { event ->
                when (event) {
                    is MainViewModel.HomeScreenEvent.Success -> {
                        binding.loader.clearAnimation()
                        binding.loader.visibility = View.GONE
                        binding.currentTemp.text = event.data.temp
                        binding.cityName.text = Config.city
                        adapter.setList(event.data.forecast)
                        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                    }
                    is MainViewModel.HomeScreenEvent.Failure -> {
                        //navigate to Error
                        Log.e(mainViewModel.TAG, event.errorText)
                        val errIntent = Intent(this@MainActivity, ErrorActivity::class.java)
                        startActivity(errIntent)
                    }
                    is MainViewModel.HomeScreenEvent.Loading -> {
                        binding.loader.visibility = View.VISIBLE
                    }
                }
            }
        }
    }
}