package com.yukle.qrcodescanner.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.yukle.qrcodescanner.data.local.base.BaseDao
import com.yukle.qrcodescanner.data.local.entity.EntityQrCode
import kotlinx.coroutines.flow.Flow

@Dao
interface QrCodeDao : BaseDao<EntityQrCode> {


    @Query("SELECT * FROM qr_code ORDER BY uid DESC ")
    fun getAllQrCodes(): Flow<List<EntityQrCode>>


}