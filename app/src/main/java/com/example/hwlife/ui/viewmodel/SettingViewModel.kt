package com.example.hwlife.ui.viewmodel

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hwlife.util.Const

class SettingViewModel(val ac: Activity) : ViewModel() {

    private val TAG = "***SettingViewModel"

    fun back() = ac.finish()

    // 도움말
    fun question(){
        Toast.makeText(ac, "마켓 등록시 구현", Toast.LENGTH_SHORT).show()
    }

    // 문의하기
    fun sendEmail() {
        val email = Intent(Intent.ACTION_SEND)
        email.apply {
            putExtra(Intent.EXTRA_EMAIL, arrayOf(Const.EMAIL_ADDRESS))
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
            type = "text/plain"
            `package` = "com.google.android.gm"
        }
        ac.startActivity(email)
    }

    // 추천
    fun recommend(){
        Toast.makeText(ac, "마켓 등록시 구현", Toast.LENGTH_SHORT).show()
    }

    // 공유하기
    fun share(){
        Toast.makeText(ac, "마켓 등록시 구현", Toast.LENGTH_SHORT).show()

        /*
        val msg = Intent(Intent.ACTION_SEND)
        msg.addCategory(Intent.CATEGORY_DEFAULT)
        msg.putExtra(
            Intent.EXTRA_TEXT,
            ""
        )
        msg.putExtra(Intent.EXTRA_TITLE, "제목")
        msg.type = "text/plain"
        ac.startActivity(Intent.createChooser(msg, "Shared"))*/
    }

    // 카페
    fun cafe(){
        ac.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://cafe.naver.com/lifeafter")))
    }


}

class SettingViewModelFactory(private val ac: Activity) : ViewModelProvider.Factory {
    @SuppressWarnings("unchecked")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            SettingViewModel(ac) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}