package com.example.hwlife.db

import androidx.lifecycle.LiveData
import com.example.hwlife.model.Level
import com.example.hwlife.model.Map

interface IDataSource {

    fun getMapData(level : String) : LiveData<MutableList<Map>>

    fun getLevelData() : LiveData<MutableList<Level>>

}