package com.example.hwlife.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.hwlife.R
import com.example.hwlife.databinding.ActivityAlarmBinding
import com.example.hwlife.viewmodel.AlarmViewModel
import com.example.hwlife.viewmodel.AlarmViewModelFactory

class AlarmActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityAlarmBinding
    private lateinit var model: AlarmViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_alarm)

        model = ViewModelProvider(this, AlarmViewModelFactory(this@AlarmActivity)).get(AlarmViewModel::class.java)

        binding.apply {
            lifecycleOwner = this@AlarmActivity
            vm = model
        }
    }
}