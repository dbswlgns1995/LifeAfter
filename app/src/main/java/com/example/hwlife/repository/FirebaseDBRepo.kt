package com.example.hwlife.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hwlife.model.Level
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class FirebaseDBRepo(context : Context) {

    private val TAG : String = "***FirebaseDBRepo"

    companion object{

        @Volatile private var instance: FirebaseDBRepo? = null

        fun getInstance(context : Context) : FirebaseDBRepo =
            instance ?: synchronized(this){
                instance ?: FirebaseDBRepo(context).also {
                    instance = it
                }
            }

    }


    fun getLevelData() : LiveData<MutableList<Level>> {
        val mutableData = MutableLiveData<MutableList<Level>>()

        FirebaseDatabase.getInstance().getReference().child("db").child("level").addListenerForSingleValueEvent(
            object : ValueEventListener {

                override fun onDataChange(datasnapshot: DataSnapshot) {
                    val listData = mutableListOf<Level>()

                    for(snapshot in datasnapshot.children){
                        val lv = snapshot.value.toString().toInt()
                        val lvmodel = Level(lv = lv)
                        listData.add(lvmodel)
                    }
                    mutableData.value = listData
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d(TAG, "get level db failed")
                }
            })

        return mutableData
    }
}