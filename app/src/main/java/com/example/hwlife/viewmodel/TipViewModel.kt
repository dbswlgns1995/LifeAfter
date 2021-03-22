package com.example.hwlife.viewmodel

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TipViewModel(val ac: Activity) : ViewModel() {

    private val TAG = "***TipViewModel"

    fun back() = ac.finish()
}

class TipViewModelFactory(private val ac: Activity) : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(TipViewModel::class.java)) {
            TipViewModel(ac) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}