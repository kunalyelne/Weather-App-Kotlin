package com.kyodude.weatherapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kyodude.weatherapp.databinding.ForecastItemBinding
import com.kyodude.weatherapp.model.dataModels.ForecastListItem
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ForecastListAdapter: RecyclerView.Adapter<ForecastListAdapter.ForecastViewHolder>() {
    private var forecastList: List<ForecastListItem> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val binding = ForecastItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ForecastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.binding.day.text = getDay(forecastList.get(position).dtTxt)
        var str =  forecastList.get(position).temp.toString() + "°C"
        holder.binding.dayTemp.text = str
    }

    override fun getItemCount(): Int {
        return forecastList.size
    }

    fun setList(list: List<ForecastListItem>) {
        this.forecastList = list
        notifyDataSetChanged()
    }

    inner class ForecastViewHolder(val binding: ForecastItemBinding): RecyclerView.ViewHolder(binding.root)

    fun getDay(dateString: String): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val parsedDate: Date = formatter.parse(dateString)
        val c = Calendar.getInstance()
        c.time = parsedDate
        when(c.get(Calendar.DAY_OF_WEEK)) {
            1 -> return "Sunday"
            2 -> return "Monday"
            3 -> return "Tuesday"
            4 -> return "Wednesday"
            5 -> return "Thursday"
            6 -> return "Friday"
            7 -> return "Saturday"
        }
        return ""
    }
}