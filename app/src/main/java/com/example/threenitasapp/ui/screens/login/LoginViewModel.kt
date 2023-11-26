package com.example.threenitasapp.ui.screens.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.threenitasapp.common.Constants
import com.example.threenitasapp.common.Resource
import com.example.threenitasapp.domain.models.LoginBody
import com.example.threenitasapp.domain.usecases.client.PasswordValidation
import com.example.threenitasapp.domain.usecases.client.UserIdValidation
import com.example.threenitasapp.domain.usecases.remote.GetUserTokenUseCase
import com.example.threenitasapp.ui.screens.login.components.LoginFormEvent
import com.example.threenitasapp.ui.screens.login.components.LoginFormState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getUserTokenUseCase: GetUserTokenUseCase,
    private val userIdValidation: UserIdValidation,
    private val passwordValidation: PasswordValidation,
) : ViewModel() {

    // Info Dialogs
    var isUserIdTextFieldDialogShown by mutableStateOf(false)
        private set

    fun onUserInfoIconClicked() {
        isUserIdTextFieldDialogShown = true
    }

    fun onUserDismissTextFieldDialog() {
        isUserIdTextFieldDialogShown = false
    }

    var isPassIdTextFieldDialogShown by mutableStateOf(false)
        private set

    fun onPassInfoIconClicked() {
        isPassIdTextFieldDialogShown = true
    }

    fun onPassDismissTextFieldDialog() {
        isPassIdTextFieldDialogShown = false
    }

    // Login Validation
    var loginFormState by mutableStateOf(LoginFormState())
    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents =validationEventChannel.receiveAsFlow()

    suspend fun getToken(loginBody: LoginBody) =
        getUserTokenUseCase(loginBody)
            .collect { response ->
                Log.d("ViewModel", "getToken: Called")
                when (response) {
                    is Resource.Success -> Log.d("ViewModel", "getToken: ${response.data}")
                    is Resource.Error -> Log.d("ViewModel", "getToken: ${response.message}")
                    is Resource.Loading -> Log.d("ViewModel", "getToken: Loading")
                }
            }

    fun checkValidation(textFieldFlag: Boolean? = null, userInput: String? = null, event: LoginFormEvent) {
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
        ).any {!it.successful}
        if (hasError){
            loginFormState = loginFormState.copy(
                userIdError = userIdResult.errorMessage,
                passwordError = passwordResult.errorMessage
            )
            return
        }
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

    sealed class ValidationEvent{
        object Success: ValidationEvent()
    }

}