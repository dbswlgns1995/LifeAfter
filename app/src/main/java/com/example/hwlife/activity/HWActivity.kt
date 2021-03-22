package com.example.hwlife.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.hwlife.R
import com.example.hwlife.databinding.ActivityHWBinding
import com.example.hwlife.hw.HWViewModel
import com.example.hwlife.hw.HWViewModelFactory

class HWActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHWBinding
    private lateinit var model: HWViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_h_w)

        model = ViewModelProvider(this, HWViewModelFactory(this@HWActivity)).get(HWViewModel::class.java)

        binding.apply {
            lifecycleOwner = this@HWActivity
            vm = model
        }
    }
}