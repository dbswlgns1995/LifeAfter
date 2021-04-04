package com.example.hwlife.ui

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.hwlife.R
import com.example.hwlife.util.Expression

class TimerDialog(context : Context, timerDialogInterface: TimerDialogInterface) : Dialog(context) {

    private val TAG = "***TimerDialog"
    private var time : Long = 0
    private var timerDialogInterface : TimerDialogInterface? = null

    init {
        this.timerDialogInterface = timerDialogInterface
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_timer)

        // 배경 투명
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val hour_edit = findViewById<EditText>(R.id.timer_hour_edit)
        val min_edit = findViewById<EditText>(R.id.timer_min_edit)
        val sec_edit = findViewById<EditText>(R.id.timer_sec_edit)

        val cancel_btn = findViewById<Button>(R.id.timer_cancel_btn)
        val ok_btn = findViewById<Button>(R.id.timer_ok_btn)

        cancel_btn.setOnClickListener { dismiss() }

        ok_btn.setOnClickListener {

            val hour = if (hour_edit.text.toString() == "") 0 else hour_edit.text.toString().toLong()
            val min = if (min_edit.text.toString() == "") 0 else min_edit.text.toString().toLong()
            val sec = if (sec_edit.text.toString() == "") 0 else sec_edit.text.toString().toLong()

            time = Expression.hmsToLong(hour, min, sec)

            this.timerDialogInterface?.onOkBtnClicked(time)
            dismiss()
        }


    }


}