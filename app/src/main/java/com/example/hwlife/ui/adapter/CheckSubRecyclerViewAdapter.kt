package com.example.hwlife.ui.adapter

import android.app.Activity
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hwlife.R
import com.example.hwlife.databinding.HwsubcheckItemBinding
import com.example.hwlife.db.repository.HWMainRepository
import com.example.hwlife.model.HWSub
import com.google.firebase.database.collection.LLRBNode

class CheckSubRecyclerViewAdapter(val ac : Activity, val maintitle : String) : RecyclerView.Adapter<CheckSubRecyclerViewAdapter.ViewHolder>(){

    private var items = listOf<HWSub>()
    private val repository : HWMainRepository = HWMainRepository(ac.application)

    fun setHWSubListData(data : List<HWSub>){
        items = data
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CheckSubRecyclerViewAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.hwsubcheck_item, parent, false)
        val viewHolder = CheckSubRecyclerViewAdapter.ViewHolder(HwsubcheckItemBinding.bind(view))
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.vm = items[position]

        if(item.isChecked){
            holder.binding.hwsubcheckText.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            holder.binding.hwsubcheckText.setTextColor(Color.RED)
        }else{
            holder.binding.hwsubcheckText.paintFlags = 0
            holder.binding.hwsubcheckText.setTextColor(Color.BLACK)
        }

        holder.binding.hwsubcheckText.setOnClickListener {
            if(item.isChecked){
                repository.updateHWSub(HWSub(item.subId, item.maintitle, item.title, item.isEnabled, false))
                holder.binding.hwsubcheckText.paintFlags = 0
                holder.binding.hwsubcheckText.setTextColor(Color.BLACK)
            }else{
                repository.updateHWSub(HWSub(item.subId, item.maintitle, item.title, item.isEnabled, true))
                holder.binding.hwsubcheckText.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                holder.binding.hwsubcheckText.setTextColor(Color.RED)
            }
        }
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(val binding : HwsubcheckItemBinding) : RecyclerView.ViewHolder(binding.root)

}