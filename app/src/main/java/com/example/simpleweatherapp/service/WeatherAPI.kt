package com.example.simpleweatherapp.service

import com.example.simpleweatherapp.model.WeatherModel
import retrofit2.Call
import retrofit2.http.GET

interface WeatherAPI {

    //sample/city.list.json.gz

    @GET(value = "sample/city.list.json.gz")
    fun getData(): Call<List<WeatherModel>>


}