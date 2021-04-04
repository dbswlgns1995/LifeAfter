package com.example.hwlife.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.hwlife.R
import com.example.hwlife.databinding.ActivityHWAddBinding
import com.example.hwlife.db.HWSubDao
import com.example.hwlife.model.HWMain
import com.example.hwlife.model.HWSub
import com.example.hwlife.ui.viewmodel.HWAddViewModel
import com.example.hwlife.ui.viewmodel.HWAddViewModelFactory
import com.example.hwlife.util.Const
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HWAddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHWAddBinding
    private lateinit var model: HWAddViewModel
    var hwMain: HWMain? = null
    var hwSub: HWSub? = null
    private var type = 0
    private var hwtitle: String = ""

    private val TAG: String = "***HWAddActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_h_w_add)

        model = ViewModelProvider(
            this,
            HWAddViewModelFactory(this@HWAddActivity)
        ).get(HWAddViewModel::class.java)

        var intent = intent

        type = intent.getIntExtra("type", 0)
        hwtitle = intent.getStringExtra("hwtitle").toString()
        setResult(RESULT_OK)

        Log.d(TAG, "hwtitle : " + hwtitle)
        if (intent != null && intent.getIntExtra("type", 0) == Const.INTENT_UPDATE_HW) {
            CoroutineScope(Dispatchers.IO).launch {
                prePoplulateData()
            }
        }

        binding.apply {
            lifecycleOwner = this@HWAddActivity
            vm = model
            hwaddAddBtn.setOnClickListener { addView() }
            hwaddSaveBtn.setOnClickListener { save() }
        }
    }


    // save each edittext into room
    private fun save() {
        if (type == 1) {
            savehw()
            finish()
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                updatehw()
                finish()
            }
        }

    }

    private fun savehw() {
        Log.d(TAG, "저장")
        var saveBoolean = true
        var editList: MutableList<String> = mutableListOf()

        for (i in 0..(binding.hwaddLinearlayout.childCount - 1)) {

            val selectedView: View = binding.hwaddLinearlayout.getChildAt(i)
            val hwadd_edit = selectedView.findViewById<EditText>(R.id.hw_sample_edit)
            if (hwadd_edit.text.isNotEmpty() && binding.hwaddTitleEdit.text.isNotEmpty()) {
                Log.d(TAG, hwadd_edit.text.toString())
                editList.add(hwadd_edit.text.toString())
            } else {
                Toast.makeText(this, "공백은 저장할 수 없습니다.", Toast.LENGTH_SHORT).show()
                saveBoolean = false
            }
        }

        if (saveBoolean) { // save to room
            Log.d(TAG, "save: ${editList}")
            model.saveHWMain(HWMain(null, binding.hwaddTitleEdit.text.toString(), true, false))
            for (i in editList) {
                model.saveHWSub(
                    HWSub(
                        null,
                        binding.hwaddTitleEdit.text.toString(),
                        i,
                        false,
                        false
                    )
                )
            }

        } else { // initialize
            editList.clear()
        }
    }

    private fun updatehw() {
        Log.d(TAG, "업데이트")

        val hwMain = model.getHWMainByTitle(hwtitle)
        model.deleteHWSubAll(hwtitle)

        var saveBoolean = true
        var editList: MutableList<String> = mutableListOf()

        for (i in 0..(binding.hwaddLinearlayout.childCount - 1)) {
            val selectedView: View = binding.hwaddLinearlayout.getChildAt(i)
            val hwadd_edit = selectedView.findViewById<EditText>(R.id.hw_sample_edit)
            if (hwadd_edit.text.isNotEmpty() && binding.hwaddTitleEdit.text.isNotEmpty()) {
                Log.d(TAG, hwadd_edit.text.toString())
                editList.add(hwadd_edit.text.toString())
            } else {
                Toast.makeText(this, "공백은 저장할 수 없습니다.", Toast.LENGTH_SHORT).show()
                saveBoolean = false
            }
        }

        if (saveBoolean) { // save to room
            Log.d(TAG, "save: ${editList}")
            model.updateHWMain(
                HWMain(
                    hwMain.mainId,
                    binding.hwaddTitleEdit.text.toString(),
                    hwMain.isdaily,
                    hwMain.isenable
                )
            )
            for (i in editList) {
                model.saveHWSub(
                    HWSub(
                        null,
                        binding.hwaddTitleEdit.text.toString(),
                        i,
                        false,
                        false
                    )
                )
            }
        } else { // initialize
            editList.clear()
        }
    }

    // create dynamic edittext
    private fun addView() {
        val add_sample: View = this.layoutInflater.inflate(R.layout.hw_sample_item, null, false)
        val hwadd_image = add_sample.findViewById<ImageView>(R.id.hw_sample_remove_image)
        hwadd_image?.setOnClickListener { remove(add_sample) }
        binding.hwaddLinearlayout.addView(add_sample)
    }

    // edit data
    private fun prePoplulateData() {
        binding.hwaddTitleEdit.setText(hwtitle)
        for (i in model.getAllHWSubList(hwtitle)) {
            val add_sample: View = this.layoutInflater.inflate(R.layout.hw_sample_item, null, false)
            val hwadd_image = add_sample.findViewById<ImageView>(R.id.hw_sample_remove_image)
            val hwadd_edit = add_sample.findViewById<EditText>(R.id.hw_sample_edit)
            hwadd_image?.setOnClickListener { remove(add_sample) }
            hwadd_edit.setText(i.title)
            binding.hwaddLinearlayout.addView(add_sample)
        }

    }

    private fun remove(view: View) {
        binding.hwaddLinearlayout.removeView(view)
    }
}