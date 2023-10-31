package tm.belet.films.data.netwrok

import android.util.Log
import com.yukle.qrcodescanner.data.netwrok.AuthInterceptor2
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.*
import javax.inject.Inject


class KtorClient @Inject constructor(
    private val authInterceptor: AuthInterceptor
) {
    companion object {
        const val TAG = "KtorClient"
    }


    fun getInstance(): HttpClient {
        return client
    }

    @OptIn(ExperimentalSerializationApi::class)
    private val client = HttpClient(OkHttp) {
        val authInterceptor2 = AuthInterceptor2()
        install(ContentNegotiation) {
            json(Json {
                isLenient = true
                ignoreUnknownKeys = true
                explicitNulls = false
            })
        }


        install(Logging) {
            level = LogLevel.ALL
            logger = object : Logger {
                override fun log(message: String) {
                    Log.d("KtorLogging", message)
                }
            }
        }
        engine {
            config {
                addInterceptor(authInterceptor)
                addInterceptor(authInterceptor2)
            }
        }
    }


}
