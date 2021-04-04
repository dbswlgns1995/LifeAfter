package com.example.hwlife.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Entity
data class HWMain(
    @PrimaryKey(autoGenerate = true) val mainId : Long?,
    @ColumnInfo(name = "title") val title : String,
    @ColumnInfo(name = "isdaily") val isdaily : Boolean,
    @ColumnInfo(name = "isenable") val isenable : Boolean,
)
