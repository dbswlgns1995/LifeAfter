package com.example.hwlife.ui.viewmodel

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hwlife.db.repository.HWMainRepository
import com.example.hwlife.model.HWMain
import com.example.hwlife.model.HWSub

class HWAddViewModel(val ac: Activity) : ViewModel() {

    private val TAG = "***HWAddViewModel"
    private val repository : HWMainRepository = HWMainRepository(ac.application)

    fun back() = ac.finish()

    fun saveHWMain(hwMain: HWMain){
        repository.saveHWMain(hwMain)
    }

    fun updateHWMain(hwMain: HWMain){
        repository.updateHWMain(hwMain)
    }

    fun saveHWSub(hwSub: HWSub){
        repository.saveHWSub(hwSub)
    }

    fun getAllHWSubList(maintitle : String) : List<HWSub>{
        return repository.getAllHWSubList(maintitle)
    }

    fun getHWMainByTitle(title : String) : HWMain{
        return repository.getHWMainByTitle(title)
    }

    fun deleteHWSubAll(maintitle: String){
        repository.deleteHWSubAll(maintitle)
    }

}

class HWAddViewModelFactory(private val ac: Activity) : ViewModelProvider.Factory{
    @SuppressWarnings("unchecked")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HWAddViewModel::class.java)) {
            HWAddViewModel(ac) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}