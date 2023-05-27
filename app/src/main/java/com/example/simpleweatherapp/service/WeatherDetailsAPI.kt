package com.example.simpleweatherapp.service

import com.example.simpleweatherapp.model.WeatherDetailsModel
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherDetailsAPI {


    //https://api.openweathermap.org/data/2.5/weather?q=antalya&APPID=ee72c7f8e3854fc5e45d66523ff727f4
    @GET("data/2.5/weather?&units=metric&APPID=ee72c7f8e3854fc5e45d66523ff727f4")
    fun getData(
        @Query("q") cityName: String
    ): Call<WeatherDetailsModel>
}