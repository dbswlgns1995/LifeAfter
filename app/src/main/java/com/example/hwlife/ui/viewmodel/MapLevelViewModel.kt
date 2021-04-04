package com.example.hwlife.ui.viewmodel

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hwlife.model.Map
import com.example.hwlife.db.repository.FirebaseRepo

class MapLevelViewModel(val ac: Activity, val title : String) : ViewModel() {

    private val TAG = "***MapLevelViewModel"

    private val firebaseRepo = FirebaseRepo.getInstance(ac.applicationContext)

    fun back() = ac.finish()

    fun fecthMapData(level: String) : LiveData<MutableList<Map>> {
        val mutableData = MutableLiveData<MutableList<Map>>()
        firebaseRepo.getMapData(level).observeForever {
            mutableData.value = it
        }
        return mutableData
    }

}

class MapLevelViewModelFactory(private val ac: Activity, private  val title:String) : ViewModelProvider.Factory{
    @SuppressWarnings("unchecked")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MapLevelViewModel::class.java)) {
            MapLevelViewModel(ac, title) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}