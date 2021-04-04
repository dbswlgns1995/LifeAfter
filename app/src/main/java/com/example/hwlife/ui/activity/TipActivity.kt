package com.example.hwlife.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hwlife.R
import com.example.hwlife.databinding.ActivityTipBinding
import com.example.hwlife.db.repository.HWMainRepository
import com.example.hwlife.ui.adapter.TipFolderRecyclerViewAdpater
import com.example.hwlife.ui.viewmodel.TipViewModel
import com.example.hwlife.ui.viewmodel.TipViewModelFactory

class TipActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityTipBinding
    private lateinit var model: TipViewModel
    private lateinit var tipFolderRecyclerViewAdpater: TipFolderRecyclerViewAdpater

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tip)

        model = ViewModelProvider(this, TipViewModelFactory(this@TipActivity)).get(TipViewModel::class.java)

        tipFolderRecyclerViewAdpater = TipFolderRecyclerViewAdpater(this)

        binding.apply {
            lifecycleOwner = this@TipActivity
            vm = model

            tipRecyclerview.apply {
                layoutManager = LinearLayoutManager(this@TipActivity)
                setHasFixedSize(true)
                adapter = tipFolderRecyclerViewAdpater
                observeData()
            }


        }

    }

    private fun observeData(){
        model.fetchTipFolderData().observe(this, Observer {
            tipFolderRecyclerViewAdpater.setListData(it)
            tipFolderRecyclerViewAdpater.notifyDataSetChanged()
            binding.tipProgressBar.visibility = View.INVISIBLE
        })
    }
}