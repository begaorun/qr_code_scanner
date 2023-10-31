package com.yukle.qrcodescanner.domain.repository

import io.ktor.client.statement.HttpResponse

interface QrCodeRepository {
    suspend fun sendQrCode(qrCode: String): HttpResponse
}