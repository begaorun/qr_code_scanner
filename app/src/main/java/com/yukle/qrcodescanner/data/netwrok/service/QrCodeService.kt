package com.yukle.qrcodescanner.data.netwrok.service

import io.ktor.client.statement.HttpResponse

interface QrCodeService {

    suspend fun sendQrCode(qrCode: String): HttpResponse

}