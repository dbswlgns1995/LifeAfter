package com.example.hwlife.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.hwlife.R
import com.example.hwlife.databinding.ActivityMainBinding
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    // mainactivity databinding
    private lateinit var binding : ActivityMainBinding

    // menu cardview
    private lateinit var cardviewList : List<View>

    private val TAG = "***MainActivity"

    private var isConnected by Delegates.notNull<Boolean>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.main = this@MainActivity

        // network checking
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        isConnected = activeNetwork?.isConnectedOrConnecting == true

    }

    override fun onResume() {
        super.onResume()
        if (isConnected){
            Log.d(TAG, "인터넷 연결")
        }else{
            Log.d(TAG, "인터넷 미연결")
            Toast.makeText(this, "인터넷 연결 상태를 확인해주세요", Toast.LENGTH_LONG).show()
        }
    }

    // main to each activity
    fun startAc(num : Int){
        Log.d(TAG, "startAc: ${num}")
        when(num){
            0->{startActivity(Intent(this, HWActivity::class.java))}
            1->{startActivity(Intent(this, HWSettingActivity::class.java))}
            2->{startActivity(Intent(this, MapActivity::class.java))}
            3->{startActivity(Intent(this, GatheringActivity::class.java))}
            4->{startActivity(Intent(this, TipActivity::class.java))}
            5->{startActivity(Intent(this, TipRequestActivity::class.java))}
            6->{startActivity(Intent(this, AlarmActivity::class.java))}
            7->{startActivity(Intent(this, SettingActivity::class.java))}
        }
    }


}