package com.example.hwlife.ui.viewmodel

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hwlife.db.repository.HWMainRepository
import com.example.hwlife.model.HWMain
import com.example.hwlife.model.HWSub
import com.example.hwlife.ui.activity.HWAddActivity
import com.example.hwlife.util.Const

class HWSettingViewModel(val ac: Activity) : ViewModel() {

    private val TAG = "***HWSettingViewModel"

    private val repository : HWMainRepository = HWMainRepository(ac.application)

    fun back() = ac.finish()

    fun addHw(){
        Log.d(TAG, "hwadd btn clicked")
        val intent = Intent(ac, HWAddActivity::class.java)
        intent.putExtra("type", Const.INTENT_CREATE_HW)
        ac.startActivityForResult(intent, Const.HW_REQUEST_CODE)
    }

}

class HWSettingViewModelFactory(private val ac: Activity) : ViewModelProvider.Factory{
    @SuppressWarnings("unchecked")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HWSettingViewModel::class.java)) {
            HWSettingViewModel(ac) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}