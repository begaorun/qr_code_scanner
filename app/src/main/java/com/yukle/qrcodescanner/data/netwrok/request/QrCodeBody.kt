package com.yukle.qrcodescanner.data.netwrok.request

import kotlinx.serialization.Serializable

@Serializable
data class QrCodeBody(
    val text: String
)
