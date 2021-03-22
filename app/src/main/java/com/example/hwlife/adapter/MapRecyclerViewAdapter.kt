package com.example.hwlife.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hwlife.R
import com.example.hwlife.activity.MapActivity
import com.example.hwlife.databinding.LevelItemBinding
import com.example.hwlife.model.Level
import com.example.hwlife.model.Map
import com.example.hwlife.repository.FirebaseStorageRepo
import com.example.hwlife.viewmodel.MapViewModel
import com.example.hwlife.viewmodel.MapViewModelFactory


class MapRecyclerViewAdapter(var ct : Context, var activity: MapActivity) : RecyclerView.Adapter<MapRecyclerViewAdapter.ViewHolder>(){

    private var items = mutableListOf<Level>()
    private val TAG : String = "***MapRecyclerViewAdapter"
    private lateinit var viewmodel : MapViewModel

    fun setLevelListData(data : MutableList<Level>){
        items = data
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MapRecyclerViewAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.level_item, parent, false)
        val viewHolder = ViewHolder(LevelItemBinding.bind(view))
        viewmodel = ViewModelProvider(activity, MapViewModelFactory(activity)).get(MapViewModel::class.java)

        return viewHolder
    }

    override fun onBindViewHolder(holder: MapRecyclerViewAdapter.ViewHolder, position: Int) {
        holder.binding.vm = items[position]
        holder.binding.levelitemLvText.setOnClickListener {
            viewmodel.mapToMapLevel(items[position].lv.toString())
            Log.d(TAG, items[position].lv.toString())
        }
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(val binding : LevelItemBinding) : RecyclerView.ViewHolder(binding.root)


}
