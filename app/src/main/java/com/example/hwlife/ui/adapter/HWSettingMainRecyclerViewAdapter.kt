package com.example.hwlife.ui.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hwlife.R
import com.example.hwlife.databinding.HwmainItemBinding
import com.example.hwlife.db.repository.HWMainRepository
import com.example.hwlife.model.HWMain
import com.example.hwlife.ui.activity.HWAddActivity
import com.example.hwlife.ui.viewmodel.HWSettingViewModel
import com.example.hwlife.ui.viewmodel.HWSettingViewModelFactory
import com.example.hwlife.util.Const
import kotlinx.coroutines.*

class HWSettingMainRecyclerViewAdapter(val ac: Activity, val lifecycleOwner: LifecycleOwner) :
    RecyclerView.Adapter<HWSettingMainRecyclerViewAdapter.ViewHolder>() {

    private lateinit var hwSettingSubRecyclerViewAdapter: HWSettingSubRecyclerViewAdapter
    private val repository: HWMainRepository = HWMainRepository(ac.application)
    private lateinit var model: HWSettingViewModel


    private var items = listOf<HWMain>()

    fun setHWMainListData(data: List<HWMain>) {
        items = data
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HWSettingMainRecyclerViewAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.hwmain_item, parent, false)
        val viewHolder = HWSettingMainRecyclerViewAdapter.ViewHolder(HwmainItemBinding.bind(view))
        return viewHolder
    }


    override fun onBindViewHolder(
        holder: HWSettingMainRecyclerViewAdapter.ViewHolder,
        position: Int
    ) {
        val item = items[position]

        hwSettingSubRecyclerViewAdapter = HWSettingSubRecyclerViewAdapter(ac, item.title)
        holder.binding.vm = item

        /*
        CoroutineScope(Dispatchers.IO).launch {
            holder.binding.hwmainRecylcerview.apply {
                layoutManager = GridLayoutManager(ac, 2)
                adapter = hwSettingSubRecyclerViewAdapter
                observeData(item.title)
            }
        }

         */

        fun start() = runBlocking {
            val job = GlobalScope.launch {
                observeData(item.title)
            }
            job.join()
            holder.binding.hwmainRecylcerview.apply {
                layoutManager = GridLayoutManager(ac, 2)
                adapter = hwSettingSubRecyclerViewAdapter
            }
        }

        start()


        holder.binding.hwmainItemRemoveImage.setOnClickListener {
            repository.deleteHWSubAll(item.title)
            repository.deleteHWMain(item)
            notifyDataSetChanged()
            hwSettingSubRecyclerViewAdapter.notifyDataSetChanged()
            ac.finish();
            ac.startActivity(ac.intent);
        }



        holder.binding.hwmainEditImage.setOnClickListener {
            val intent = Intent(ac, HWAddActivity::class.java)
            intent.putExtra("type", Const.INTENT_UPDATE_HW)
            intent.putExtra("hwtitle", item.title)
            ac.startActivityForResult(intent, Const.HW_REQUEST_CODE)
        }

        holder.binding.hwmainItemCheckbox.setOnCheckedChangeListener { _, b ->
            if (b) {
                repository.updateHWMain(HWMain(item.mainId, item.title, item.isdaily, true))
            } else {
                repository.updateHWMain(HWMain(item.mainId, item.title, item.isdaily, false))
            }
        }
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(val binding: HwmainItemBinding) : RecyclerView.ViewHolder(binding.root)

    private fun observeData(maintitle: String) {

        /*
        repository.getAllHWSubList(maintitle).observe(lifecycleOwner, Observer {
            hwSettingSubRecyclerViewAdapter.setHWSubListData(it)
        })*/

        hwSettingSubRecyclerViewAdapter.setHWSubListData(repository.getAllHWSubList(maintitle))
        //hwSettingSubRecyclerViewAdapter.notifyDataSetChanged()

    }

}