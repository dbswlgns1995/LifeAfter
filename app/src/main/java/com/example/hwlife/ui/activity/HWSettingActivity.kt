package com.example.hwlife.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hwlife.R
import com.example.hwlife.databinding.ActivityHWSettingBinding
import com.example.hwlife.db.repository.HWMainRepository
import com.example.hwlife.ui.adapter.HWSettingMainRecyclerViewAdapter
import com.example.hwlife.ui.viewmodel.HWSettingViewModel
import com.example.hwlife.ui.viewmodel.HWSettingViewModelFactory
import com.example.hwlife.util.Const
import kotlinx.coroutines.*

class HWSettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHWSettingBinding
    private lateinit var model: HWSettingViewModel
    private lateinit var hwSettingMainRecyclerViewAdapter: HWSettingMainRecyclerViewAdapter
    val TAG : String = "***HWSettingActivity"
    private lateinit var repository : HWMainRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_h_w_setting)

        repository = HWMainRepository(application)

        model = ViewModelProvider(this, HWSettingViewModelFactory(this@HWSettingActivity)).get(
            HWSettingViewModel::class.java
        )

        hwSettingMainRecyclerViewAdapter = HWSettingMainRecyclerViewAdapter(this@HWSettingActivity, this)

        binding.apply {
            lifecycleOwner = this@HWSettingActivity
            vm = model

        }

        fun start() = runBlocking {
            val job = GlobalScope.launch {
                observeData()
            }
            job.join()
            binding.hwsettingRecyclerview.apply {
                layoutManager = LinearLayoutManager(this@HWSettingActivity)
                adapter= hwSettingMainRecyclerViewAdapter
            }
        }

        start()
    }

    private fun observeData() {

        /*
        repository.getAllHWMainList().observe(this, Observer {
            hwSettingMainRecyclerViewAdapter.setHWMainListData(it)
            //hwSettingMainRecyclerViewAdapter.notifyDataSetChanged()
        })
        */

        CoroutineScope(Dispatchers.IO).launch {
            hwSettingMainRecyclerViewAdapter.setHWMainListData(repository.getAllHWMainList())
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == Const.HW_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                Log.d(TAG, "성공")
                finish()
                startActivity(this.intent)
            }else{
                Log.d(TAG, "실패")
            }
        }
    }
}