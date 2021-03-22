package com.example.hwlife.viewmodel

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SettingViewModel(val ac: Activity) : ViewModel() {

    private val TAG = "***SettingViewModel"

    fun back() = ac.finish()
}

class SettingViewModelFactory(private val ac: Activity) : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            SettingViewModel(ac) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}