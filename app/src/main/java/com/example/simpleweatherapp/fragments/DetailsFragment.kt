package com.example.simpleweatherapp.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import com.example.simpleweatherapp.R
import com.example.simpleweatherapp.databinding.FragmentDetailsBinding
import com.example.simpleweatherapp.databinding.FragmentListBinding
import com.example.simpleweatherapp.model.WeatherDetailsModel
import com.example.simpleweatherapp.service.WeatherAPI
import com.example.simpleweatherapp.service.WeatherDetailsAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class DetailsFragment : Fragment() {


    //https://api.openweathermap.org/data/2.5/weather?q=antalya&APPID=ee72c7f8e3854fc5e45d66523ff727f4

    private val BASE_URL = "https://api.openweathermap.org/"

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cityName = requireActivity().intent.getStringExtra("city")
        if (cityName != null) {
            loadData(cityName)
        }else{
            loadData("istanbul")
        }
    }


    private fun loadData(cityName: String) {

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(WeatherDetailsAPI::class.java)
        val call = service.getData(cityName)

        call.enqueue(object : Callback<WeatherDetailsModel>{
            override fun onResponse(
                call: Call<WeatherDetailsModel>,
                response: Response<WeatherDetailsModel>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        binding.cityName.text = it.name
                        binding.current.text = "Current: " + it.main.temp.toInt() + " C"
                        binding.H.text = "H: " + it.main.temp_max + " C"
                        binding.L.text = "L: " + it.main.temp_min + " C"
                        binding.feelsLike.text= "Feels Like: " + it.main.feels_like + " C"


                        binding.humidity.text = "Humidity:                                                                           " + it.main.humidity + "C"
                        binding.speed.text = "Speed: " + it.wind.speed.toString() + " km/h"
                        binding.gust.text = "Gust: " + it.wind.deg.toString() + " km/h"
                        binding.seaLevel.text = "Sea Level:                                                                  "  + it.main.pressure.toString()
                        binding.latitude.text = "Latitude: " + it.coord.lat.toInt()
                        binding.longitude.text = "Longitude: " + it.coord.lon.toInt()
                        binding.description.text= it.weather[0].description

                    }
                }
            }

            override fun onFailure(call: Call<WeatherDetailsModel>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }








}