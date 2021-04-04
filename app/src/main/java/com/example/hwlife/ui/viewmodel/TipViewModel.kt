package com.example.hwlife.ui.viewmodel

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hwlife.db.repository.FirebaseRepo
import com.example.hwlife.model.TipFolder
import com.example.hwlife.ui.activity.TipImageActivity

class TipViewModel(val ac: Activity) : ViewModel() {

    private val TAG = "***TipViewModel"
    private val firebaseRepo = FirebaseRepo.getInstance(ac.applicationContext)

    fun back() = ac.finish()

    fun fetchTipFolderData() : LiveData<MutableList<TipFolder>>{
        val mutableData = MutableLiveData<MutableList<TipFolder>>()
        firebaseRepo.getTipFolder().observeForever {
            mutableData.value = it
        }

        return mutableData
    }
}

class TipViewModelFactory(private val ac: Activity) : ViewModelProvider.Factory{
    @SuppressWarnings("unchecked")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(TipViewModel::class.java)) {
            TipViewModel(ac) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}