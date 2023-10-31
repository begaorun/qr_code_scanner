package tm.belet.films.data.repository

import com.yukle.qrcodescanner.data.netwrok.service.QrCodeService
import com.yukle.qrcodescanner.domain.repository.QrCodeRepository
import io.ktor.client.statement.HttpResponse
import javax.inject.Inject

class QrCodeRepositoryImpl @Inject constructor(
    private val qrCodeService: QrCodeService,
) : QrCodeRepository {

    override suspend fun sendQrCode(qrCode: String): HttpResponse {
        return qrCodeService.sendQrCode(qrCode)
    }

}