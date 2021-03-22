package com.example.hwlife.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hwlife.R
import com.example.hwlife.adapter.MapRecyclerViewAdapter
import com.example.hwlife.databinding.ActivityMapBinding
import com.example.hwlife.viewmodel.MapViewModel
import com.example.hwlife.viewmodel.MapViewModelFactory
import com.google.firebase.FirebaseApp

class MapActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMapBinding
    private lateinit var model: MapViewModel
    private lateinit var mapRecyclerViewAdapter: MapRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_map)

        FirebaseApp.initializeApp(this)

        model = ViewModelProvider(this, MapViewModelFactory(this@MapActivity)).get(MapViewModel::class.java)
        mapRecyclerViewAdapter = MapRecyclerViewAdapter(this, this@MapActivity)

        binding.apply {
            lifecycleOwner = this@MapActivity
            vm = model
        }

        binding.mapRecyclerview.apply {
            layoutManager = LinearLayoutManager(this@MapActivity)
            adapter = mapRecyclerViewAdapter
            observeLevelData()
        }

    }

    fun observeLevelData(){
        model.fetchLevelData().observe(this, Observer {
            mapRecyclerViewAdapter.setLevelListData(it)
            mapRecyclerViewAdapter.notifyDataSetChanged()
        })
    }
}