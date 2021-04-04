package com.example.hwlife.ui.activity

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.hwlife.R
import com.example.hwlife.databinding.ActivityAlarmBinding
import com.example.hwlife.ui.AlarmClass
import com.example.hwlife.ui.TimerDialog
import com.example.hwlife.ui.TimerDialogInterface
import com.example.hwlife.ui.viewmodel.AlarmViewModel
import com.example.hwlife.ui.viewmodel.AlarmViewModelFactory
import com.example.hwlife.util.Const
import com.example.hwlife.util.Expression
import java.text.SimpleDateFormat
import java.util.*

class AlarmActivity : AppCompatActivity(), TimerDialogInterface {

    private lateinit var binding: ActivityAlarmBinding
    private lateinit var model: AlarmViewModel

    private var alarmManager: AlarmManager? = null
    private lateinit var pendingIntent: PendingIntent

    private lateinit var time_text: TextView
    private lateinit var sharedPref: SharedPreferences
    private var text: String? = null


    private val TAG = "***AlarmActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_alarm)

        sharedPref = getSharedPreferences("pref", Context.MODE_PRIVATE) ?: return
        text = sharedPref.getString(getString(R.string.sharedpref_string1), "00:00:00")

        model = ViewModelProvider(
            this,
            AlarmViewModelFactory(this@AlarmActivity)
        ).get(AlarmViewModel::class.java)

        binding.apply {
            lifecycleOwner = this@AlarmActivity
            vm = model
        }

        time_text = findViewById(R.id.time_text)
        time_text.setOnClickListener {
            val timerDialog = TimerDialog(this, this)
            timerDialog.show()
        }

        if (text.toString() == "00:00:00") {

            binding.alarmCancelBtn.visibility = View.INVISIBLE

            model.time.observe(this, androidx.lifecycle.Observer { t ->

                binding.timeText.text = Expression.timeToText(t)

                if (t > 0) {
                    binding.alarmStartBtn.setOnClickListener {
                        alarmSetting(t)
                        finish()
                    }
                }
            })

        } else {
            isContinued()
        }

        binding.alarmCancelBtn.setOnClickListener {
            cancel()
        }


    }

    private fun isContinued() {
        binding.alarmCancelBtn.visibility = View.VISIBLE
        binding.timeText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24f)
        binding.timeText.text = text.toString()
        binding.timeText.setOnClickListener { }
        binding.alarmStartBtn.visibility = View.INVISIBLE
        binding.alarmMinusBtn.visibility = View.INVISIBLE
        binding.alarmPlusBtn.visibility = View.INVISIBLE
    }

    private fun cancel() {
        model.setTime(0)
        with(sharedPref.edit()) {
            putString(getString(R.string.sharedpref_string1), "00:00:00")
            commit()
        }
        binding.timeText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 60f)
        binding.timeText.text = ("00:00:00")
        alarmManager?.cancel(pendingIntent)

        finish()

    }

    private fun alarmSetting(time: Long) {

        // text setting
        val formatter = SimpleDateFormat("MM월 dd일 HH:mm:ss", Locale.KOREA);
        val date = Date()
        val new_date = date.time + time
        val currentDate = formatter.format(new_date);

        binding.timeText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24f)
        binding.timeText.text = "$currentDate 에 \n알람이 울립니다."

        with(sharedPref.edit()) {
            putString(getString(R.string.sharedpref_string1), "$currentDate 에 \n알람이 울립니다.")
            commit()
        }

        // alarm setting

        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        pendingIntent = Intent(this, AlarmClass::class.java).let { intent ->
            intent.putExtra(Const.ALARMMANAGER_KEY, Const.INTENT_ALARM)
                        PendingIntent.getBroadcast(this, 0, intent, 0)
        }

        // Starts the alarm manager
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            alarmManager?.setExact(
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + time,
                pendingIntent
            )
        }

        Toast.makeText(
            this, "$currentDate 에 \n" +
                    "알람이 울립니다.", Toast.LENGTH_LONG
        ).show()

    }


    override fun onOkBtnClicked(time: Long) {
        model.setTime(time)
    }

}