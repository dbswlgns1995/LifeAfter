package com.example.hwlife.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HWSub(
    @PrimaryKey(autoGenerate = true) val subId : Long?,
    val maintitle : String ="",
    val title : String="",
    val isEnabled : Boolean=false,
    val isChecked : Boolean=false)