package com.example.wsrtwo.MainMenu.Menu

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.view.menu.MenuAdapter
import androidx.core.app.NotificationCompat.getCategory
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wsrtwo.Api.RetrofitClient
import com.example.wsrtwo.Data.CatalogItem
import com.example.wsrtwo.R
import com.example.wsrtwo.databinding.FragmentMenuBinding
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch
import retrofit2.http.Query
import kotlin.jvm.internal.FunInterfaceConstructorReference
@SuppressLint("NotifyDataSetChanged")
class MenuFragment : Fragment() {

    private lateinit var firstAdapter: AdapterFirstRecycler
    private lateinit var secondAdapter: AdapterSecondRecycler
    private lateinit var thirdAdapter: AdapterThirdRecycler

    private lateinit var recyclerViewFirst: RecyclerView
    private lateinit var recyclerViewSecond:RecyclerView
    private lateinit var recyclerViewThird: RecyclerView

    private lateinit var addBtn:MaterialButton
    private lateinit var FL: FrameLayout

    private var _binding:FragmentMenuBinding?=null
    private val binding get()= _binding!!

    private var searchQuery: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMenuBinding.inflate(layoutInflater,container,false)
        //Первый ресайклер вью
        recyclerViewFirst = binding.firstRecyclerView
        recyclerViewFirst.layoutManager =
            LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)
        firstAdapter = AdapterFirstRecycler(emptyList())
        recyclerViewFirst.adapter = firstAdapter

        //Второй ресайлкер вью
        recyclerViewSecond = binding.secondRecyclerView
        recyclerViewSecond.layoutManager = LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)
        secondAdapter = AdapterSecondRecycler { onItemOnClick->
            val catalog = getCategory(onItemOnClick)
            thirdAdapter.analysisList = catalog
            thirdAdapter.notifyDataSetChanged()
            recyclerViewThird.adapter = thirdAdapter
        }
        recyclerViewSecond.adapter = secondAdapter

        //Третий ресайклер вью
        recyclerViewThird = binding.thirdRecyclerView
        recyclerViewThird.layoutManager = LinearLayoutManager(requireContext())
        addBtn = binding.addBtn
        FL = binding.FL
        thirdAdapter = AdapterThirdRecycler(emptyList(),addBtn,FL)
        recyclerViewThird.adapter = thirdAdapter


        val searchView = binding.searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                searchQuery = newText.toString()
                filterData(searchQuery)
                return true
            }

            private fun filterData(query:String){
                val filterList = mutableListOf<CatalogItem>()
                for(item in thirdAdapter.analysisList){
                    if(item.name.contains(query,true)){
                        filterList.add(item)
                    }
                }
                thirdAdapter.analysisList = filterList
                thirdAdapter.notifyDataSetChanged()
            }
        })
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val apiClientThird = RetrofitClient.apiService
        lifecycleScope.launch {
            val response = apiClientThird.getCatalog()
            val catalog = response.body()
            if(response.isSuccessful && !catalog.isNullOrEmpty()){
                thirdAdapter.analysisList = catalog
                thirdAdapter.notifyDataSetChanged()
            }else{
                //error
            }
        }
        val apiClientFirst = RetrofitClient.apiService
        lifecycleScope.launch {
            val responseFirst = apiClientFirst.getNews()
            if(responseFirst.isSuccessful){
                val catalogFirst = responseFirst.body()
                firstAdapter.newsList = catalogFirst ?: emptyList()
                firstAdapter.notifyDataSetChanged()
            }else{
                //error
            }
        }
    }

    private fun getCategory (category:String):List<CatalogItem>{
        val list = mutableListOf<CatalogItem>()
        val apiClient = RetrofitClient.apiService
        lifecycleScope.launch {
            val response = apiClient.getCatalog()
            val catalog = response.body()
            if(response.isSuccessful && !catalog.isNullOrEmpty()){

                for (item in catalog){
                    if(item.category == category){
                        list.add(item)
                    }
                }
                thirdAdapter.analysisList = list
                thirdAdapter.notifyDataSetChanged()
            }else{
                Toast.makeText(requireContext(),"error",Toast.LENGTH_SHORT).show()
            }
        }
        return list
    }
    override fun onDestroy() {
        super.onDestroy()
       _binding = null
    }
}