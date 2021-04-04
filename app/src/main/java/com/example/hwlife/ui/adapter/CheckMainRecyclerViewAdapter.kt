package com.example.hwlife.ui.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.hwlife.R
import com.example.hwlife.databinding.HwmaincheckItemBinding
import com.example.hwlife.db.AppDatabase
import com.example.hwlife.db.HWSubDao
import com.example.hwlife.db.repository.HWMainRepository
import com.example.hwlife.model.HWMain
import kotlinx.coroutines.*

class CheckMainRecyclerViewAdapter(var ac: Activity, var lifecycleOwner: LifecycleOwner) :
    RecyclerView.Adapter<CheckMainRecyclerViewAdapter.ViewHolder>() {

    private lateinit var checkSubRecyclerViewAdapter: CheckSubRecyclerViewAdapter
    private val repository: HWMainRepository = HWMainRepository(ac.application)

    private var items = listOf<HWMain>()

    fun setHWMainListData(data: List<HWMain>) {
        items = data
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CheckMainRecyclerViewAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.hwmaincheck_item, parent, false)
        val viewHolder = CheckMainRecyclerViewAdapter.ViewHolder(HwmaincheckItemBinding.bind(view))
        return viewHolder
    }

    override fun onBindViewHolder(holder: CheckMainRecyclerViewAdapter.ViewHolder, position: Int) {
        val item = items[position]

        checkSubRecyclerViewAdapter = CheckSubRecyclerViewAdapter(ac, item.title)

        holder.binding.vm = item

        fun start() = runBlocking {
            val job = GlobalScope.launch {
                observeData(item.title)
            }
            job.join()
            holder.binding.hwmaincheckRecylcerview.apply {
                layoutManager = LinearLayoutManager(ac)
                adapter = checkSubRecyclerViewAdapter
            }
        }

        start()

        /*
        CoroutineScope(Dispatchers.IO).launch {
            holder.binding.hwmaincheckRecylcerview.apply {
                layoutManager = LinearLayoutManager(ac)
                adapter = checkSubRecyclerViewAdapter
                observeData(item.title)
            }
        }*/


    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(val binding: HwmaincheckItemBinding) : RecyclerView.ViewHolder(binding.root)

    private fun observeData(maintitle: String) {

        /*
        repository.getAllHWSubList(maintitle).observe(lifecycleOwner, Observer {
            checkSubRecyclerViewAdapter.setHWSubListData(it)
        })*/
        checkSubRecyclerViewAdapter.setHWSubListData(repository.getAllHWSubList(maintitle))
        checkSubRecyclerViewAdapter.notifyDataSetChanged()

    }

}