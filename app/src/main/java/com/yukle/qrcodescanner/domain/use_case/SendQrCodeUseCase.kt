package tm.belet.films.domain.use_case

import com.yukle.qrcodescanner.data.Resource
import com.yukle.qrcodescanner.data.response.ServerMsg
import com.yukle.qrcodescanner.domain.repository.QrCodeRepository
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.serialization.JsonConvertException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.SerializationException
import java.io.IOException
import javax.inject.Inject

class SendQrCodeUseCase @Inject constructor(
    private val playerRepository: QrCodeRepository
) {

    operator fun invoke(
        qrCode: String
    ): Flow<Resource<ServerMsg>> =
        flow {
            try {
                emit(Resource.Loading())
                val data: ServerMsg =
                    playerRepository.sendQrCode(qrCode)
                        .body()
                emit(Resource.Success(data))
            } catch (e: ClientRequestException) {
                emit(Resource.Error(e.message.toString()))
            } catch (e: ServerResponseException) {
                emit(Resource.Error(e.message.toString()))
            } catch (e: JsonConvertException) {
                emit(Resource.Error(e.message.toString()))
            } catch (e: NoTransformationFoundException) {
                emit(Resource.Error(e.message.toString()))
            } catch (e: IllegalStateException) {
                emit(Resource.Error(e.message.toString()))
            } catch (e: SerializationException) {
                emit(Resource.Error(e.message.toString()))
            } catch (e: IOException) {
                emit(Resource.Error(e.message.toString()))
            }
        }
}