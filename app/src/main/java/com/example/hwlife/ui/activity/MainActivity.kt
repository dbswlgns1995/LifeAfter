package com.example.hwlife.ui.activity

import android.animation.ValueAnimator
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import com.example.hwlife.ui.ResetClass
import com.example.hwlife.util.Const
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import java.util.*
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    // mainactivity databinding
    private lateinit var binding : ActivityMainBinding

    // menu cardview
    private lateinit var cardviewList : List<View>

    private val TAG = "***MainActivity"

    private var isConnected by Delegates.notNull<Boolean>()

    private var isLiked : Boolean = false

    private var alarmManager: AlarmManager? = null
    private lateinit var pendingIntent: PendingIntent

    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseinit()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.main = this@MainActivity

        // network checking
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        isConnected = activeNetwork?.isConnectedOrConnecting == true

        val animator = ValueAnimator.ofFloat(0.6f, 1f).setDuration(1500) // onfloat 어디에서 어디까지, setDuration 1000 1초
        animator.addUpdateListener {    it ->
            binding.mainAnimationView.progress = it.animatedValue as Float
        }

        binding.mainAnimationView.setOnClickListener {
            if(isLiked == false){
                animator.start()
                isLiked = true
            }else isLiked = false
        }

        sharedPref = getSharedPreferences("pref", Context.MODE_PRIVATE) ?: return

        // 앱 다운시 한번만 설정
        if(!sharedPref.getBoolean("reset", false)){
            reset()
        }


    }

    // firebase 초기화
    private fun firebaseinit(){
        FirebaseApp.initializeApp(this);
    }

    override fun onResume() {
        super.onResume()
        if(!isConnected) Toast.makeText(this, "인터넷 연결 상태를 확인해주세요", Toast.LENGTH_LONG).show()
    }

    // main to each activity
    fun startAc(num : Int){
        Log.d(TAG, "startAc: ${num}")
        when(num){
            0->{startActivity(Intent(this, HWActivity::class.java))}
            1->{startActivity(Intent(this, HWSettingActivity::class.java))}
            2->{startActivity(Intent(this, MapActivity::class.java))}
            3->{startActivity(Intent(this, TipActivity::class.java))}
            4->{startActivity(Intent(this, AlarmActivity::class.java))}
            5->{startActivity(Intent(this, SettingActivity::class.java))}
        }
    }

    // 숙제 초기화
    private fun reset() {

        Log.d(TAG, "reset 완료")

        with(sharedPref.edit()) {
            putBoolean("reset", true)
            commit()
        }

        // 새벽 3시에 초기화
        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 3)
        }

        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        pendingIntent = Intent(this, ResetClass::class.java).let { intent ->
            intent.putExtra(Const.ALARMMANAGER_KEY, Const.INTENT_RESET)
            PendingIntent.getBroadcast(this, 0, intent, 0)
        }

        alarmManager?.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )

    }


}