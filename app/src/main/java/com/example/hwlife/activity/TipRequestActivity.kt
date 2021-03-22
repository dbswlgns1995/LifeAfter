package com.example.hwlife.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.hwlife.R
import com.example.hwlife.databinding.ActivityTipRequestBinding
import com.example.hwlife.viewmodel.TipRequestViewModel
import com.example.hwlife.viewmodel.TipRequestViewModelFactory

class TipRequestActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityTipRequestBinding
    private lateinit var model: TipRequestViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tip_request)

        model = ViewModelProvider(this, TipRequestViewModelFactory(this@TipRequestActivity)).get(TipRequestViewModel::class.java)

        binding.apply {
            lifecycleOwner = this@TipRequestActivity
            vm = model
        }
    }
}