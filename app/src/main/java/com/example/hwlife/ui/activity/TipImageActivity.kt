package com.example.hwlife.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hwlife.R
import com.example.hwlife.databinding.ActivityTipImageBinding
import com.example.hwlife.ui.adapter.TipImageRecyclerViewAdapter
import com.example.hwlife.ui.viewmodel.TipImageViewModel
import com.example.hwlife.ui.viewmodel.TipImageViewModelFactory

class TipImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTipImageBinding
    private lateinit var model: TipImageViewModel
    private lateinit var tipImageRecyclerViewAdapter: TipImageRecyclerViewAdapter
    private lateinit var folder : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tip_image)

        folder = intent.getStringExtra("folder").toString()

        tipImageRecyclerViewAdapter = TipImageRecyclerViewAdapter(this, folder)
        model = ViewModelProvider(
            this,
            TipImageViewModelFactory(this@TipImageActivity)
        ).get(TipImageViewModel::class.java)

        binding.apply {
            lifecycleOwner = this@TipImageActivity
            vm = model

            binding.tipimageRecyclerview.apply {
                layoutManager = LinearLayoutManager(this@TipImageActivity)
                adapter = tipImageRecyclerViewAdapter
                observeData(folder)
            }
        }
    }

    private fun observeData(foldername : String) {
        model.fetchTipImageData(foldername).observe(this, Observer {
            tipImageRecyclerViewAdapter.setListData(it)
            tipImageRecyclerViewAdapter.notifyDataSetChanged()
        })
    }
}