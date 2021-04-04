package com.example.hwlife.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.hwlife.model.HWSub

@Dao
interface HWSubDao {
    @Query("SELECT *  FROM HWSub where maintitle = :maintitle")
    fun getAllHWSub(maintitle : String) : List<HWSub>

    @Insert
    fun saveHWSub(hwSub: HWSub)

    @Delete
    fun deleteHWSub(hwSub: HWSub)

    @Query("DELETE FROM HWSub WHERE maintitle = :maintitle")
    fun deleteHWSubAll(maintitle: String)

    @Update
    fun updateHWSub(hwSub: HWSub)

    @Query("UPDATE HWSub SET isChecked = :fa")
    fun resetAllHWSub(fa : Boolean = false)
}