package com.example.hwlife.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hwlife.R
import com.example.hwlife.adapter.MapLevelRecyclerViewAdapter
import com.example.hwlife.adapter.MapRecyclerViewAdapter
import com.example.hwlife.databinding.ActivityMapLevelBinding
import com.example.hwlife.viewmodel.MapLevelViewModel
import com.example.hwlife.viewmodel.MapLevelViewModelFactory

class MapLevelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMapLevelBinding
    private lateinit var model: MapLevelViewModel
    private lateinit var title : String
    private lateinit var mapLevelRecyclerViewAdapter: MapLevelRecyclerViewAdapter
    private var TAG : String = "***MapLevelActivity"
    private lateinit var level : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_map_level)

        level = intent.getStringExtra("level").toString()
        title = "Level ${level}"

        model = ViewModelProvider(
            this,
            MapLevelViewModelFactory(this@MapLevelActivity, title)
        ).get(MapLevelViewModel::class.java)

        mapLevelRecyclerViewAdapter = MapLevelRecyclerViewAdapter(this)

        binding.apply {
            lifecycleOwner = this@MapLevelActivity
            vm = model
        }

        binding.maplevelRecyclerview.apply {
            layoutManager = LinearLayoutManager(this@MapLevelActivity)
            adapter = mapLevelRecyclerViewAdapter
            observeData()
        }
    }

    fun observeData(){
        model.fecthMapData(level).observe(this, Observer {
            mapLevelRecyclerViewAdapter.setMapLevelListData(it)
            mapLevelRecyclerViewAdapter.notifyDataSetChanged()
        })
    }

}