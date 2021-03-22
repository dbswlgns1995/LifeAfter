package com.example.hwlife.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.hwlife.R
import com.example.hwlife.databinding.ActivitySettingBinding
import com.example.hwlife.viewmodel.SettingViewModel
import com.example.hwlife.viewmodel.SettingViewModelFactory

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding
    private lateinit var model: SettingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting)

        model = ViewModelProvider(
            this,
            SettingViewModelFactory(this@SettingActivity)
        ).get(SettingViewModel::class.java)

        binding.apply {
            lifecycleOwner = this@SettingActivity
            vm = model
        }
    }
}