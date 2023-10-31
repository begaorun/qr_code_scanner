package com.yukle.qrcodescanner.data.local

import androidx.room.Room
import com.yukle.qrcodescanner.App

object DatabaseHolder {
    val Database by lazy {
        Room.databaseBuilder(App.instance, AppDatabase::class.java, "QrCode")
            .fallbackToDestructiveMigration()
            .build()
    }
}
