package com.example.hwlife.db.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.hwlife.db.AppDatabase
import com.example.hwlife.db.HWMainDao
import com.example.hwlife.db.HWSubDao
import com.example.hwlife.model.HWMain
import com.example.hwlife.model.HWSub
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HWMainRepository(application: Application) {

    private val hwMainDao: HWMainDao
    private val hwSubDao: HWSubDao

    init {
        val database = AppDatabase.getInstance(application.applicationContext)
        hwMainDao = database!!.hwMainDao()
        hwSubDao = database.hwSubDao()
    }


    fun resetAllHWSub() = runBlocking {
        this.launch(Dispatchers.IO) {
            hwSubDao.resetAllHWSub()
        }
    }

    fun saveHWMain(hwMain: HWMain) = runBlocking {
        this.launch(Dispatchers.IO) {
            hwMainDao.saveHWMain(hwMain)
        }
    }

    fun updateHWMain(hwMain: HWMain) = runBlocking {
        this.launch(Dispatchers.IO) {
            hwMainDao.updateHWMain(hwMain)
        }
    }

    fun deleteHWMain(hwMain: HWMain) {
        runBlocking {
            this.launch(Dispatchers.IO) {
                hwMainDao.deleteHWMain(hwMain)
            }
        }
    }


    /*
    // livedata
    fun getEnabledHWMainList() : LiveData<List<HWMain>>{
        return hwMainDao.getEnabledHWMainList()
    }*/

    // coroutine
    fun getEnabledHWMainList() : List<HWMain>{
        return hwMainDao.getEnabledHWMainList()
    }

    //livedata
    /*
    fun getAllHWMainList(): LiveData<List<HWMain>> {
        return hwMainDao.getAllHWMainList()
    }*/


    //coroutine
    fun getAllHWMainList(): List<HWMain> {
        return hwMainDao.getAllHWMainList()
    }

    fun getHWMainByTitle(title: String): HWMain {
        return hwMainDao.getHWMainByTitle(title)
    }

    fun saveHWSub(hwSub: HWSub) = runBlocking {
        this.launch(Dispatchers.IO) {
            hwSubDao.saveHWSub(hwSub)
        }
    }

    fun updateHWSub(hwSub: HWSub) = runBlocking {
        this.launch(Dispatchers.IO) {
            hwSubDao.updateHWSub(hwSub)
        }
    }

    fun deleteHWSubAll(maintitle: String) = runBlocking {
        this.launch(Dispatchers.IO) {
            hwSubDao.deleteHWSubAll(maintitle)
        }
    }

    fun deleteHWSub(hwSub: HWSub) {
        runBlocking {
            this.launch(Dispatchers.IO) {
                hwSubDao.deleteHWSub(hwSub)
            }
        }
    }

    fun getAllHWSubList(maintitle: String): List<HWSub> {
        return hwSubDao.getAllHWSub(maintitle)
    }
}