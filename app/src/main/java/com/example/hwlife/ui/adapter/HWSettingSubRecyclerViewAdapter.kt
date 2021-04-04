package com.example.hwlife.ui.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.hwlife.R
import com.example.hwlife.databinding.HwmainItemBinding
import com.example.hwlife.databinding.HwsubItemBinding
import com.example.hwlife.databinding.LevelItemBinding
import com.example.hwlife.db.repository.HWMainRepository
import com.example.hwlife.model.HWMain
import com.example.hwlife.model.HWSub
import com.example.hwlife.ui.viewmodel.MapViewModel
import com.example.hwlife.ui.viewmodel.MapViewModelFactory

class HWSettingSubRecyclerViewAdapter(val ac : Activity, val maintitle : String) : RecyclerView.Adapter<HWSettingSubRecyclerViewAdapter.ViewHolder>(){

    private var items = listOf<HWSub>()
    private val repository : HWMainRepository = HWMainRepository(ac.application)

    fun setHWSubListData(data : List<HWSub>){
        items = data
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HWSettingSubRecyclerViewAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.hwsub_item, parent, false)
        val viewHolder = HWSettingSubRecyclerViewAdapter.ViewHolder(HwsubItemBinding.bind(view))
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.vm = items[position]
        holder.binding.hwsubItemCheckbox.setOnCheckedChangeListener { _, b ->
            if (b){
                repository.updateHWSub(HWSub(item.subId, item.maintitle, item.title, true, item.isChecked))
            }else{
                repository.updateHWSub(HWSub(item.subId, item.maintitle, item.title, false, item.isChecked))
            }
        }

    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(val binding : HwsubItemBinding) : RecyclerView.ViewHolder(binding.root)

}