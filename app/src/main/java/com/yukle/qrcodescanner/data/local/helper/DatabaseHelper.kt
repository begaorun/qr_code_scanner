package com.yukle.qrcodescanner.data.local.helper

import com.yukle.qrcodescanner.data.local.DatabaseHolder.Database
import com.yukle.qrcodescanner.data.local.entity.EntityQrCode
import kotlinx.coroutines.flow.Flow


object DatabaseHelper {

    fun addQrCode(text: String) {
        Database.qrCodeDao()?.insert(EntityQrCode(text))
    }

    fun getAllQrCodes(): Flow<List<EntityQrCode>>? {
        return Database.qrCodeDao()?.getAllQrCodes()
    }


}
