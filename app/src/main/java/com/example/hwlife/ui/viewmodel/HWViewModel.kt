package com.example.hwlife.hw

import android.app.Activity
import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class HWViewModel(val ac: Activity) : ViewModel() {

    private val TAG = "***HWViewModel"

    fun back() = ac.finish()
}

class HWViewModelFactory(private val ac: Activity) : ViewModelProvider.Factory{
    @SuppressWarnings("unchecked")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HWViewModel::class.java)) {
            HWViewModel(ac) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}