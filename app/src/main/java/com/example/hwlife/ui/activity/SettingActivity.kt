package com.example.hwlife.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.example.hwlife.R
import com.example.hwlife.databinding.ActivitySettingBinding
import com.example.hwlife.db.repository.FirebaseRepo
import com.example.hwlife.model.Tip
import com.example.hwlife.ui.viewmodel.SettingViewModel
import com.example.hwlife.ui.viewmodel.SettingViewModelFactory
import com.google.firebase.storage.StorageReference

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