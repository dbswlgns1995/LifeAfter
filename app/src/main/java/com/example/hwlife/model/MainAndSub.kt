package com.example.hwlife.model

import androidx.room.Embedded
import androidx.room.Relation

data class MainAndSub(
    @Embedded val hwMain: HWMain,
    @Relation(
        parentColumn = "MainId",
        entityColumn = "subId"
    )
    val listOfHWSub: List<HWSub>
)
