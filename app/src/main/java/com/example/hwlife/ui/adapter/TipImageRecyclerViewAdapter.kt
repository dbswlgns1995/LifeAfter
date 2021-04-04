package com.example.hwlife.ui.adapter

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hwlife.R
import com.example.hwlife.databinding.TipItemBinding
import com.example.hwlife.model.Tip
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

class TipImageRecyclerViewAdapter(private val ac : Activity, private  val folder : String) : RecyclerView.Adapter<TipImageRecyclerViewAdapter.ViewHolder>(){

    private lateinit var storageRef : StorageReference
    private val TAG = "***TipImageRecyclerViewAdapter"
    private var items = mutableListOf<Tip>()

    fun setListData(data : MutableList<Tip>){
        items = data
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TipImageRecyclerViewAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tip_item, parent, false)
        val viewHolder = TipImageRecyclerViewAdapter.ViewHolder(TipItemBinding.bind(view))

        storageRef = Firebase.storage.reference

        return viewHolder
    }

    override fun onBindViewHolder(holder: TipImageRecyclerViewAdapter.ViewHolder, position: Int) {
        holder.binding.vm = items[position]

        val spaceRef = storageRef.child(items[position].url)

        spaceRef.downloadUrl.addOnCompleteListener { task ->
            if(task.isSuccessful){
                Glide.with(ac.applicationContext)
                    .load(task.result)
                    .into(holder.binding.tipitemImageview)
            }else {
                Log.d(TAG, "getTipItem error")
            }
        }
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(val binding : TipItemBinding) : RecyclerView.ViewHolder(binding.root)
}

