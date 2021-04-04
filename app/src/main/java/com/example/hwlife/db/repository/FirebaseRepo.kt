package com.example.hwlife.db.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hwlife.db.IDataSource
import com.example.hwlife.model.Level
import com.example.hwlife.model.Map
import com.example.hwlife.model.Tip
import com.example.hwlife.model.TipFolder
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

class FirebaseRepo(val context: Context) : IDataSource {

    private val dbRef : DatabaseReference = FirebaseDatabase.getInstance().getReference()
    private val storageRef : StorageReference = Firebase.storage.reference

    private val TAG : String = "@@@FirebaseRepo"

    companion object{

        @Volatile private var instance: FirebaseRepo? = null

        fun getInstance(context : Context) : FirebaseRepo =
            instance ?: synchronized(this){
                instance ?: FirebaseRepo(context).also {
                    instance = it
                }
            }

    }

    // 지도 가져오기
    override fun getMapData(level: String): LiveData<MutableList<Map>> {
        val mutableData = MutableLiveData<MutableList<Map>>()
        val path : String = "map/"+level
        storageRef.child(path).listAll().addOnSuccessListener {

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


    // tip folder 이름 가져오기
    fun getTipFolder() : LiveData<MutableList<TipFolder>>{
        val mutableData = MutableLiveData<MutableList<TipFolder>>()

        storageRef.child("tip").listAll().addOnSuccessListener {

            val listData = mutableListOf<TipFolder>()

            for (i in it.prefixes){
                listData.add(TipFolder(i.name))
            }

            mutableData.value = listData

        }.addOnFailureListener {
            Log.d(TAG, "tip folder 이름 가져오기 실패")
        }

        return mutableData
    }


    // tip item 가져오기
    fun getTipItem(folder : String) : LiveData<MutableList<Tip>>{
        val mutableData = MutableLiveData<MutableList<Tip>>()

        storageRef.child("tip/${folder}").listAll().addOnSuccessListener {
            val listData = mutableListOf<Tip>()

            for (i in it.items){
                listData.add(Tip(i.path.substring(1)))
            }
            mutableData.value = listData

        }.addOnFailureListener {
            Log.d(TAG, "tip image 가져오기 실패")
        }
        return mutableData
    }


    // level data 가져오기
    override fun getLevelData(): LiveData<MutableList<Level>> {
        val mutableData = MutableLiveData<MutableList<Level>>()

        dbRef.child("db").child("level").addListenerForSingleValueEvent(
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