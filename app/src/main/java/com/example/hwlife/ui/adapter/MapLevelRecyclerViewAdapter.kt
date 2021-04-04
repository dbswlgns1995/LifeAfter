package com.example.hwlife.ui.adapter

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hwlife.R
import com.example.hwlife.databinding.MapItemBinding
import com.example.hwlife.model.Map
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage


class MapLevelRecyclerViewAdapter(val context: Context) :
    RecyclerView.Adapter<MapLevelRecyclerViewAdapter.ViewHolder>() {

    private val TAG : String = "***MapLevelRecyclerViewAdapter"
    private var items = mutableListOf<Map>()
    private lateinit var storageRef : StorageReference

    fun setMapLevelListData(data: MutableList<Map>){
        items = data
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MapLevelRecyclerViewAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.map_item, parent, false)
        val viewHolder = ViewHolder(MapItemBinding.bind(view))

        storageRef = Firebase.storage.reference

        return viewHolder
    }

    override fun onBindViewHolder(holder: MapLevelRecyclerViewAdapter.ViewHolder, position: Int) {
        holder.binding.vm = items[position]

        holder.binding.mapitemText.text = items[position].map.replace(".PNG","")

        val spaceRef = storageRef.child(items[position].url)

        spaceRef.downloadUrl.addOnCompleteListener(OnCompleteListener<Uri?> { task ->
            if (task.isSuccessful) {
                Glide.with(context)
                    .load(task.result)
                    .into(holder.binding.mapitemIamgeview)
            } else {
                Log.d(TAG, "error")
            }
        })



    }

    override fun getItemCount(): Int = items.size


    class ViewHolder(val binding: MapItemBinding) : RecyclerView.ViewHolder(binding.root)
}