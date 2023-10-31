package tm.belet.films.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yukle.qrcodescanner.data.Resource
import com.yukle.qrcodescanner.data.response.ServerMsg
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import tm.belet.films.domain.use_case.SendQrCodeUseCase
import javax.inject.Inject

@HiltViewModel
class MovieInfoViewModel @Inject constructor(
    private val sendQrCodeUseCase: SendQrCodeUseCase
) : ViewModel() {
    data class sendQrCodeStateList(
        val isLoading: Boolean = false, val data: ServerMsg? = null, val error: String = ""
    )


    private val _sendQrCodeValue = MutableStateFlow(sendQrCodeStateList())
    var sendQrCodeValue: StateFlow<sendQrCodeStateList> = _sendQrCodeValue


    fun sendQrCode(qrCode: String) = viewModelScope.launch {
        sendQrCodeUseCase(qrCode).collect {
            when (it) {
                is Resource.Success -> {
                    _sendQrCodeValue.value = sendQrCodeStateList(data = it.data)
                }

                is Resource.Loading -> {
                    _sendQrCodeValue.value = sendQrCodeStateList(isLoading = true)
                }

                is Resource.Error -> {
                    _sendQrCodeValue.value =
                        sendQrCodeStateList(error = it.message ?: "An UnexpectedError")
                }
            }
        }
    }

}