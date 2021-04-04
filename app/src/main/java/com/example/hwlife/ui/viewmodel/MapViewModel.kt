package com.example.hwlife.ui.viewmodel

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hwlife.ui.activity.MapLevelActivity
import com.example.hwlife.model.Level
import com.example.hwlife.db.repository.FirebaseRepo

class MapViewModel(val ac: Activity) : ViewModel() {

    private val TAG = "***MapViewModel"

    private val firebaseRepo = FirebaseRepo.getInstance(ac.applicationContext)

    fun back() = ac.finish()

    fun fetchLevelData() : LiveData<MutableList<Level>>{
        val mutableData = MutableLiveData<MutableList<Level>>()
        firebaseRepo.getLevelData().observeForever {
            mutableData.value = it
        }
        return mutableData
    }
    fun mapToMapLevel(level: String){
        val intent = Intent(ac, MapLevelActivity::class.java)
        intent.putExtra("level", level)
        ac.startActivity(intent)
    }



}

class MapViewModelFactory(private val ac: Activity) : ViewModelProvider.Factory{
    @SuppressWarnings("unchecked")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MapViewModel::class.java)) {
            MapViewModel(ac) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}