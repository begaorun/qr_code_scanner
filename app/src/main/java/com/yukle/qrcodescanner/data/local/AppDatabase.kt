package com.yukle.qrcodescanner.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yukle.qrcodescanner.data.local.entity.EntityQrCode
import com.yukle.qrcodescanner.data.local.dao.QrCodeDao

@Database(
    entities = [
        EntityQrCode::class
    ],
    version = 1,
    exportSchema = true

)
abstract class AppDatabase : RoomDatabase() {
    abstract fun qrCodeDao(): QrCodeDao?
}
