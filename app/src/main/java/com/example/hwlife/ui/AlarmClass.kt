package com.example.hwlife.ui

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.example.hwlife.R
import com.example.hwlife.db.repository.HWMainRepository
import com.example.hwlife.ui.activity.MainActivity
import com.example.hwlife.util.Const
import com.example.hwlife.util.vectorToBitmap

class AlarmClass : BroadcastReceiver() {

    private val TAG = "***BroadCastClass"

    // alarm
    private var text: String? = null
    private lateinit var sharedPref: SharedPreferences

    override fun onReceive(context: Context?, intent: Intent?) {

        Log.d(TAG, "알람")

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

        // sharedpreference 초기화
        sharedPref = context?.getSharedPreferences("pref", Context.MODE_PRIVATE) ?: return
        text = sharedPref.getString(context.getString(R.string.sharedpref_string1), "00:00:00")
        if (text != "00:00:00") {
            with(sharedPref.edit()) {
                putString(context.getString(R.string.sharedpref_string1), "00:00:00")
                commit()
            }
        }

        val bitmap = context.vectorToBitmap(R.drawable.ic_schedule_black_24dp)
        //notification
        val builder = NotificationCompat.Builder(
            context, "default"
        ).apply {
            setSmallIcon(R.drawable.ic_schedule_white)
            setLargeIcon(bitmap)
            setContentTitle(context.getString(R.string.alarm_noti_title))
            setContentText(context.getString(R.string.alarm_noti_content))
            setAutoCancel(true)
            setContentIntent(pendingIntent)
            setDefaults(NotificationCompat.DEFAULT_ALL)
        }

        val notificationManager =
            context.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager

        // 알람음음
        val ringtoneManager = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val audioAttributes =
            AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_NOTIFICATION_RINGTONE)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(
                    "default",
                    "basic channel",
                    NotificationManager.IMPORTANCE_DEFAULT
                ).apply {
                    enableVibration(true) // 진동
                    enableLights(true)
                    lightColor = Color.RED
                    setSound(ringtoneManager, audioAttributes)
                }

            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(1, builder.build())


    }


}