package com.example.hwlife.viewmodel

import android.app.Activity
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hwlife.dialog.HWAddDialog

class HWSettingViewModel(val ac: Activity) : ViewModel() {

    private val TAG = "***HWSettingViewModel"

    public fun back() = ac.finish()

    public fun addHw(){
        Log.d(TAG, "hwadd btn clicked")
        val hwAddDialog = HWAddDialog(ac)
        hwAddDialog.show()
    }
}

class HWSettingViewModelFactory(private val ac: Activity) : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HWSettingViewModel::class.java)) {
            HWSettingViewModel(ac) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}