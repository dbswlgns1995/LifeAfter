package com.example.hwlife.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hwlife.R
import com.example.hwlife.ui.adapter.MapRecyclerViewAdapter
import com.example.hwlife.databinding.ActivityMapBinding
import com.example.hwlife.ui.viewmodel.MapViewModel
import com.example.hwlife.ui.viewmodel.MapViewModelFactory
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
            layoutManager = GridLayoutManager(this@MapActivity, 3)
            adapter = mapRecyclerViewAdapter
            observeLevelData()
        }

    }

    private fun observeLevelData(){
        model.fetchLevelData().observe(this, Observer {
            mapRecyclerViewAdapter.setLevelListData(it)
            mapRecyclerViewAdapter.notifyDataSetChanged()
            binding.mapProgressBar.visibility = View.INVISIBLE
        })
    }
}