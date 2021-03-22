package com.example.hwlife.viewmodel

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hwlife.hw.HWViewModel

class GatheringViewModel(val ac: Activity) : ViewModel() {

    private val TAG = "***GatheringViewModel"

    fun back() = ac.finish()
}

class GatheringViewModelFactory(private val ac: Activity) : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(GatheringViewModel::class.java)) {
            GatheringViewModel(ac) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}