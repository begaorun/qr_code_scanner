package com.yukle.qrcodescanner

import android.app.Application
import androidx.room.Room
import com.yukle.qrcodescanner.data.local.AppDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    private var db: AppDatabase? = null

    override fun onCreate() {
        super.onCreate()
        initVariables()
    }

    private fun initVariables() {
        db = Room.databaseBuilder(this, AppDatabase::class.java, "QrCode").build()
        instance = this
    }

    companion object {
        lateinit var instance: App
    }

}