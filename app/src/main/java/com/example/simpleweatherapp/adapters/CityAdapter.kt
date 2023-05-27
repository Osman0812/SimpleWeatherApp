package com.example.simpleweatherapp.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.simpleweatherapp.MainActivity
import com.example.simpleweatherapp.R
import com.example.simpleweatherapp.databinding.ItemCityBinding
import com.example.simpleweatherapp.fragments.DetailsFragment
import com.example.simpleweatherapp.fragments.ListFragmentDirections
import com.example.simpleweatherapp.model.WeatherModel

class CityAdapter(private val context: Context, private var cityList: ArrayList<WeatherModel>): RecyclerView.Adapter<CityAdapter.RowHolder>() {


    class RowHolder(val binding: ItemCityBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val binding = ItemCityBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return RowHolder(binding)
    }

    override fun getItemCount(): Int {
        return cityList.count()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filterList(filterlist: ArrayList<WeatherModel>) {
        // below line is to add our filtered
        // list in our course array list.
        cityList = filterlist
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {

        holder.binding.cityName.text = cityList.get(position).name

        holder.itemView.setOnClickListener {
            val  intent = Intent(holder.itemView.context, MainActivity::class.java)
            intent.putExtra("city",cityList.get(position).name)
            holder.itemView.context.startActivity(intent)

        }




    }


}