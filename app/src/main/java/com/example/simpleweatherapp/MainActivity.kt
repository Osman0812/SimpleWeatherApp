package com.example.simpleweatherapp

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.simpleweatherapp.databinding.ActivityMainBinding
import com.example.simpleweatherapp.fragments.DetailsFragment
import com.example.simpleweatherapp.fragments.DetailsFragmentDirections
import com.example.simpleweatherapp.fragments.ListFragment
import com.example.simpleweatherapp.fragments.ListFragmentDirections
import com.example.simpleweatherapp.model.WeatherDetailsModel
import com.example.simpleweatherapp.model.WeatherModel
import com.example.simpleweatherapp.service.WeatherAPI
import com.example.simpleweatherapp.service.WeatherDetailsAPI
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.nio.charset.StandardCharsets

class MainActivity : AppCompatActivity() {

    //ee72c7f8e3854fc5e45d66523ff727f4
    //https://api.openweathermap.org/

    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNav : BottomNavigationView

    private val BASE_URL = "https://api.openweathermap.org/"
    private var weatherModels : ArrayList<WeatherModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //loadData()
        //readJSONFromAssets()


        //changeFragmentTo(DetailsFragment())


        bottomNav = binding.bottomNavigationView.findViewById(R.id.bottomNavigationView)

        bottomNav.setOnItemSelectedListener {

            when (it.itemId){

                R.id.cloud -> {


                    changeFragmentTo(DetailsFragment())

                }
                R.id.list -> {

                    changeFragmentTo(ListFragment())



                }

            }

            true
        }

       //
    }


    private fun changeFragmentTo(fragment : Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.fragmentContainerView,fragment).commit()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        return super.onCreateOptionsMenu(menu)
    }


    private fun loadData() {

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(WeatherDetailsAPI::class.java)
        val call = service.getData("tabi")

        call.enqueue(object : Callback<WeatherDetailsModel>{
            override fun onResponse(
                call: Call<WeatherDetailsModel>,
                response: Response<WeatherDetailsModel>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        println(it.timezone.toString())

                    }
                }
            }

            override fun onFailure(call: Call<WeatherDetailsModel>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }


}