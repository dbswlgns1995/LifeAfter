package com.example.hwlife.ui.viewmodel

import android.app.Activity
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hwlife.db.repository.FirebaseRepo
import com.example.hwlife.model.Tip

class TipImageViewModel(val ac: Activity) : ViewModel() {

    private val TAG = "***TipImageViewModel"
    private val firebaseRepo = FirebaseRepo.getInstance(ac.applicationContext)

    fun back() = ac.finish()


    fun fetchTipImageData(folder : String) : LiveData<MutableList<Tip>> {
        val mutableData = MutableLiveData<MutableList<Tip>>()
        firebaseRepo.getTipItem(folder).observeForever {
            mutableData.value = it
        }

        return mutableData
    }
}

class TipImageViewModelFactory(private val ac: Activity) : ViewModelProvider.Factory{
    @SuppressWarnings("unchecked")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(TipImageViewModel::class.java)) {
            TipImageViewModel(ac) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}