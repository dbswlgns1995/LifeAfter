package com.example.hwlife.viewmodel

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AlarmViewModel(val ac: Activity) : ViewModel() {

    private val TAG = "***AlarmViewModel"

    fun back() = ac.finish()
}

class AlarmViewModelFactory(private val ac: Activity) : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AlarmViewModel::class.java)) {
            AlarmViewModel(ac) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}