package com.example.hwlife.ui.viewmodel

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AlarmViewModel(val ac: Activity) : ViewModel() {

    private val TAG = "***AlarmViewModel"
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent

    var time = MutableLiveData<Long>()

    init {
        time.value = 0
    }

    fun setTime(t: Long) {
        time.value = t
    }

    fun plusOneMin() {
        time.value = time.value?.plus(60*1000)
    }

    fun minusOneMin() {
        if (time.value!! >= 60*1000) {
            time.value = time.value?.minus(60*1000)
        }
    }



    fun back() = ac.finish()

}


class AlarmViewModelFactory(private val ac: Activity) : ViewModelProvider.Factory {

    @SuppressWarnings("unchecked")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AlarmViewModel::class.java)) {
            AlarmViewModel(ac) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}