package com.example.hwlife.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.hwlife.R
import com.example.hwlife.ui.activity.MapActivity
import com.example.hwlife.databinding.LevelItemBinding
import com.example.hwlife.model.Level
import com.example.hwlife.ui.viewmodel.MapViewModel
import com.example.hwlife.ui.viewmodel.MapViewModelFactory


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
