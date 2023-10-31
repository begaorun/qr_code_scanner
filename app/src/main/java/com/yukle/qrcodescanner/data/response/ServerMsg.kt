package com.yukle.qrcodescanner.data.response

import kotlinx.serialization.Serializable

@Serializable
data class ServerMsg(
    val message: String?
)
