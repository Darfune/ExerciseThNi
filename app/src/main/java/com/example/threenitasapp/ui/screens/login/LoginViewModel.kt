package com.example.threenitasapp.ui.screens.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.threenitasapp.common.Resource
import com.example.threenitasapp.domain.models.LoginBody
import com.example.threenitasapp.domain.usecases.client.PasswordValidation
import com.example.threenitasapp.domain.usecases.client.UserIdValidation
import com.example.threenitasapp.domain.usecases.remote.GetUserTokenUseCase
import com.example.threenitasapp.ui.screens.login.state.LoginFormEvent
import com.example.threenitasapp.ui.screens.login.state.LoginFormState
import com.example.threenitasapp.ui.screens.login.state.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getUserTokenUseCase: GetUserTokenUseCase,
    private val userIdValidation: UserIdValidation,
    private val passwordValidation: PasswordValidation,
) : ViewModel() {

    // User Input

    private val _uiState: MutableStateFlow<LoginState> = MutableStateFlow(
        LoginState(
            userId = "",
            password = "",
        )
    )
    val uiState: StateFlow<LoginState> = _uiState.asStateFlow()

    // Info Dialogs

    var isUserIdTextFieldDialogShown by mutableStateOf(false)
        private set
    var isPassIdTextFieldDialogShown by mutableStateOf(false)
        private set
    fun onUserInfoIconClicked() {
        isUserIdTextFieldDialogShown = true
        Log.d("onUserInfoIconClicked: ", "$isUserIdTextFieldDialogShown")
    }

    fun onUserDismissTextFieldDialog() {
        isUserIdTextFieldDialogShown = false
        Log.d("onUserDismissDialog: ", "$isUserIdTextFieldDialogShown")
    }

    fun onPassInfoIconClicked() {
        isPassIdTextFieldDialogShown = true
        Log.d("onUserInfoIconClicked: ", "$isUserIdTextFieldDialogShown")
    }

    fun onPassDismissTextFieldDialog() {
        isPassIdTextFieldDialogShown = false
        Log.d("onUserInfoIconClicked: ", "$isUserIdTextFieldDialogShown")
    }

    // Login Validation
    var loginFormState by mutableStateOf(LoginFormState())
    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    suspend fun getToken() =
        getUserTokenUseCase(LoginBody(_uiState.value.userId, _uiState.value.password))
            .collect { response ->
                when (response) {
                    is Resource.Success -> Log.d("ViewModel", "getToken: ${response.data}")
                    is Resource.Error -> Log.d("ViewModel", "getToken: ${response.message}")
                    is Resource.Loading -> Log.d("ViewModel", "getToken: Loading")
                }
            }


    fun onValidationEvent(event: LoginFormEvent) {
        when (event) {
            is LoginFormEvent.UserIdChanged -> {
                loginFormState = loginFormState.copy(userId = event.userId)
            }

            is LoginFormEvent.PasswordChanged -> {
                loginFormState = loginFormState.copy(password = event.password)
            }

            is LoginFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val userIdResult = userIdValidation(loginFormState.userId)
        val passwordResult = passwordValidation(loginFormState.password)

        val hasError = listOf(
            userIdResult,
            passwordResult,
        ).any { !it.successful }
        if (hasError) {
            loginFormState = loginFormState.copy(
                userIdError = userIdResult.errorMessage,
                passwordError = passwordResult.errorMessage
            )
            viewModelScope.launch {
                validationEventChannel.send(ValidationEvent.Error)
            }
            return
        }
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

    sealed class ValidationEvent {
        object Success : ValidationEvent()
        object Error : ValidationEvent()
    }

}


