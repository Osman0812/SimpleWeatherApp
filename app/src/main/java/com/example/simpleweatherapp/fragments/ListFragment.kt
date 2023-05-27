package com.example.simpleweatherapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.simpleweatherapp.R
import com.example.simpleweatherapp.adapters.CityAdapter
import com.example.simpleweatherapp.databinding.FragmentListBinding
import com.example.simpleweatherapp.model.WeatherModel
import org.json.JSONArray
import java.io.IOException
import java.nio.charset.StandardCharsets

class ListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private lateinit var cityArrayList : ArrayList<WeatherModel>
    private lateinit var adapter : CityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cityArrayList = ArrayList()

        readJSONFromAssets()




        binding.cityRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = CityAdapter(requireContext(),cityArrayList)
        binding.cityRecyclerView.adapter = adapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun readJSONFromAssets(){
        try {
            val inputStream = requireActivity().assets.open("city.list.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()

            val json = String(buffer, StandardCharsets.UTF_8)
            val jsonArray = JSONArray(json)
            val max = jsonArray.length()
            var i = 0
            cityArrayList.clear()
            while (i < max){
                val jsonObject = jsonArray.getJSONObject(i)
                val id = jsonObject.getInt("id")
                val name = jsonObject.getString("name")
                i += 1

                val city = WeatherModel(id,name)
                cityArrayList.add(city)
                //println(name)
            }


        } catch (e: IOException) {
            e.printStackTrace()
        }


    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.search_view,menu)


        val item = menu.findItem(R.id.action_search)
        val searchView = MenuItemCompat.getActionView(item) as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                searchUsers(newText!!)
                return false
            }

        })

    }

    @SuppressLint("NotifyDataSetChanged")
     fun searchUsers(query: String){
        val filteredList = cityArrayList.filter {
            it.name.contains(query,false) }

        adapter = CityAdapter(requireContext(), filteredList as ArrayList<WeatherModel>)
        binding.cityRecyclerView.adapter = adapter
        adapter.notifyDataSetChanged()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}