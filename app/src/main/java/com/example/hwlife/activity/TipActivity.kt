package com.example.hwlife.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.hwlife.R
import com.example.hwlife.databinding.ActivityTipBinding
import com.example.hwlife.viewmodel.TipViewModel
import com.example.hwlife.viewmodel.TipViewModelFactory

class TipActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityTipBinding
    private lateinit var model: TipViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tip)

        model = ViewModelProvider(this, TipViewModelFactory(this@TipActivity)).get(TipViewModel::class.java)

        binding.apply {
            lifecycleOwner = this@TipActivity
            vm = model
        }
    }
}