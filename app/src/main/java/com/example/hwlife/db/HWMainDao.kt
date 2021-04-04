package com.example.hwlife.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.hwlife.model.HWMain

@Dao
interface HWMainDao {

    /*
    //livedata
    @Query("SELECT * FROM HWMain")
    fun getAllHWMainList(): LiveData<List<HWMain>>
     */

    // coroutine
    @Query("SELECT * FROM HWMain")
    fun getAllHWMainList(): List<HWMain>

    /*
    // livedata
    @Query("SELECT * FROM HWMain WHERE isenable = :tr")
    fun getEnabledHWMainList(tr : Boolean = true) : LiveData<List<HWMain>>

     */

    // coroutine
    @Query("SELECT * FROM HWMain WHERE isenable = :tr")
    fun getEnabledHWMainList(tr : Boolean = true) : List<HWMain>

    @Query("SELECT * FROM HWMain WHERE title = :title")
    fun getHWMainByTitle(title : String) : HWMain

    @Insert
    fun saveHWMain(hwMain: HWMain)

    @Delete
    fun deleteHWMain(hwMain: HWMain)

    @Update
    fun updateHWMain(hwMain: HWMain)


}