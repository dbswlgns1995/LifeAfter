package com.example.hwlife.viewmodel

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TipRequestViewModel(val ac: Activity) : ViewModel() {

    private val TAG = "***TipRequestViewModel"

    fun back() = ac.finish()
}

class TipRequestViewModelFactory(private val ac: Activity) : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(TipRequestViewModel::class.java)) {
            TipRequestViewModel(ac) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}