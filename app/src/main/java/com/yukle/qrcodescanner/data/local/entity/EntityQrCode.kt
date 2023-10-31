package com.yukle.qrcodescanner.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "qr_code")
class EntityQrCode(
    @ColumnInfo var text: String? = null


) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}






