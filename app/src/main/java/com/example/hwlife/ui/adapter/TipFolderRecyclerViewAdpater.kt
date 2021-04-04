package com.example.hwlife.ui.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hwlife.R
import com.example.hwlife.databinding.TipfolderItemBinding
import com.example.hwlife.model.TipFolder
import com.example.hwlife.ui.activity.TipImageActivity

class TipFolderRecyclerViewAdpater(private val ac : Activity) : RecyclerView.Adapter<TipFolderRecyclerViewAdpater.ViewHolder>(){

    private var items = mutableListOf<TipFolder>()

    fun setListData(data : MutableList<TipFolder>){
        items = data
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TipFolderRecyclerViewAdpater.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tipfolder_item, parent, false)
        val viewHolder = ViewHolder(TipfolderItemBinding.bind(view))
        return viewHolder
    }

    override fun onBindViewHolder(holder: TipFolderRecyclerViewAdpater.ViewHolder, position: Int) {
        holder.binding.vm = items[position]
        holder.binding.tipfolderText.setOnClickListener {
            val intent = Intent(ac, TipImageActivity::class.java)
            intent.putExtra("folder", items[position].title)
            ac.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(val binding : TipfolderItemBinding) : RecyclerView.ViewHolder(binding.root){

    }
}