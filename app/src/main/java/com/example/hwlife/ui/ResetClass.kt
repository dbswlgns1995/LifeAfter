package com.example.hwlife.ui

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.hwlife.db.repository.HWMainRepository

class ResetClass : BroadcastReceiver() {

    private val TAG = "***ResetClass"



    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(TAG, "reset")

        val mApplication = context?.applicationContext as Application
        val repository : HWMainRepository = HWMainRepository(mApplication)

        if (intent?.action == "android.intent.action.BOOT_COMPLETED") {
            repository.resetAllHWSub()
        }
        repository.resetAllHWSub()
    }

}