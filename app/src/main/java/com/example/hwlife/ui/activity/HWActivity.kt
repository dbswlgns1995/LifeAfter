package com.example.hwlife.ui.activity

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hwlife.R
import com.example.hwlife.databinding.ActivityHWBinding
import com.example.hwlife.db.repository.HWMainRepository
import com.example.hwlife.hw.HWViewModel
import com.example.hwlife.hw.HWViewModelFactory
import com.example.hwlife.ui.ResetClass
import com.example.hwlife.ui.adapter.CheckMainRecyclerViewAdapter
import com.example.hwlife.util.Const
import kotlinx.coroutines.*
import java.util.*

class HWActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHWBinding
    private lateinit var model: HWViewModel
    private lateinit var checkMainRecyclerViewAdapter: CheckMainRecyclerViewAdapter
    private lateinit var repository: HWMainRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_h_w)



        repository = HWMainRepository(application)

        model = ViewModelProvider(
            this,
            HWViewModelFactory(this@HWActivity)
        ).get(HWViewModel::class.java)

        checkMainRecyclerViewAdapter = CheckMainRecyclerViewAdapter(this, this)

        binding.apply {
            lifecycleOwner = this@HWActivity
            vm = model
        }
        start()

    }

    private fun start() = runBlocking {
        val job = GlobalScope.launch {
            observeData()
        }
        job.join()
        binding.hwRecyclerview.apply {
            layoutManager = LinearLayoutManager(this@HWActivity)
            adapter = checkMainRecyclerViewAdapter
        }
    }



    private fun observeData() {
        CoroutineScope(Dispatchers.IO).launch {
            checkMainRecyclerViewAdapter.setHWMainListData(repository.getEnabledHWMainList())
        }

    }
}