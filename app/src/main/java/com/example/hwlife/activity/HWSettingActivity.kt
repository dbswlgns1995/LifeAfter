package com.example.hwlife.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.hwlife.R
import com.example.hwlife.databinding.ActivityHWBinding
import com.example.hwlife.databinding.ActivityHWSettingBinding
import com.example.hwlife.hw.HWViewModel
import com.example.hwlife.hw.HWViewModelFactory
import com.example.hwlife.viewmodel.HWSettingViewModel
import com.example.hwlife.viewmodel.HWSettingViewModelFactory
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class HWSettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHWSettingBinding
    private lateinit var model: HWSettingViewModel
    val TAG : String = "***HWSettingActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_h_w_setting)

        model = ViewModelProvider(this, HWSettingViewModelFactory(this@HWSettingActivity)).get(
            HWSettingViewModel::class.java
        )

        val storage = Firebase.storage
        val storagefef = storage.reference.child("map/1")

        storagefef.listAll().addOnSuccessListener {
            for(i in it.items){
                Log.d(TAG, "${i.name}")
            }
        }.addOnFailureListener {
            Log.d(TAG, "failure")
        }


        binding.apply {
            lifecycleOwner = this@HWSettingActivity
            vm = model
        }
    }
}