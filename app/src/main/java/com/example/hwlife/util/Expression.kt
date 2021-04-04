package com.example.hwlife.util

import android.util.Log

object Expression {

    private val TAG = "***Expression"

    fun hmsToLong(hour: Long, min: Long, sec: Long) : Long{

        val h = if (hour<0) 0 else hour
        val m = if (min<0) 0 else min
        val s = if (sec<0) 0 else sec

        val time = ((h * 3600) + (m * 60) + s) * 1000

        return time
    }

    fun timeToText(time: Long) : String{

        Log.d(TAG, time.toString())

        if (time == null){
            Log.d(TAG, "null 이올시다")
            return "00:00:00"
        }else{
            val h = (time / 3600000)
            val m = (time - h * 3600000) / 60000
            val s = (time - h * 3600000 - m * 60000) / 1000
            return (if (h < 10) "0$h" else h).toString() + ":" + (if (m < 10) "0$m" else m) + ":" + if (s < 10) "0$s" else s
        }


    }
}