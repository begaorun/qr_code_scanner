package com.yukle.qrcodescanner.data.netwrok.serviceImp

import com.yukle.qrcodescanner.data.netwrok.NetworkRoutes
import com.yukle.qrcodescanner.data.netwrok.request.QrCodeBody
import com.yukle.qrcodescanner.data.netwrok.service.QrCodeService
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import tm.belet.films.data.netwrok.KtorClient
import javax.inject.Inject

class QrCodeServiceImpl @Inject constructor(
    private val httpClient: KtorClient
) : QrCodeService {

    override suspend fun sendQrCode(qrCode: String): HttpResponse {
        return httpClient.getInstance().post(NetworkRoutes.BASE_URL) {
            contentType(ContentType.Application.Json)
            setBody(QrCodeBody(qrCode))
        }
    }
}