package com.example.hwlife.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hwlife.model.Level
import com.example.hwlife.model.Map
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class FirebaseStorageRepo(context : Context) {

    private val TAG : String = "***FirebaseStorageRepo"

    companion object{

        @Volatile private var instance: FirebaseStorageRepo? = null

        fun getInstance(context : Context) : FirebaseStorageRepo =
            instance ?: synchronized(this){
                instance ?: FirebaseStorageRepo(context).also {
                    instance = it
                }
            }
    }

    fun getMapData(level : String) : LiveData<MutableList<Map>> {

        val mutableData = MutableLiveData<MutableList<Map>>()
        val path : String = "map/"+level
        val storageRef = Firebase.storage.reference


        Firebase.storage.reference.child(path).listAll().addOnSuccessListener {

            val listData = mutableListOf<Map>()
            for (item in it.items){
                // val path = storageRef.toString() + item.path.substring(1)
                //Log.d(TAG, path)
                val mapmodel = Map(item.name, item.path.substring(1))

                listData.add(mapmodel)
            }
            mutableData.value = listData

        }.addOnFailureListener {
            Log.d(TAG, "failure")
        }

        return mutableData
    }


}