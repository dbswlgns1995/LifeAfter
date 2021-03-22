package com.example.hwlife.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
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
        val spaceRef = storageRef.child(items[position].url)
        spaceRef.getDownloadUrl().addOnCompleteListener(OnCompleteListener<Uri?> { task ->
            if (task.isSuccessful) {
                Glide.with(context)
                    .load(task.result)
                    .into(holder.binding.mapitemIamgeview)
            } else {
            }
        })


    }

    override fun getItemCount(): Int = items.size


    class ViewHolder(val binding: MapItemBinding) : RecyclerView.ViewHolder(binding.root)
}