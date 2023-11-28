package com.example.threenitasapp.ui.screens.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.threenitasapp.common.Resource
import com.example.threenitasapp.domain.remote.model.LoginBody
import com.example.threenitasapp.domain.client.usecase.PasswordValidation
import com.example.threenitasapp.domain.client.usecase.UserIdValidation
import com.example.threenitasapp.domain.remote.usecase.GetUserTokenUseCase
import com.example.threenitasapp.ui.screens.login.state.LoginFormEvent
import com.example.threenitasapp.ui.screens.login.state.LoginFormState
import com.example.threenitasapp.ui.screens.login.state.LoginState
import com.example.threenitasapp.common.StateConstants
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
    private var _uiState: MutableStateFlow<LoginState> = MutableStateFlow(
        LoginState(
            userId = "",
            password = "",
            currentLanguage = true,
            isUserIdTextFieldDialogShown = false,
            isPassIdTextFieldDialogShown = false,
            isErrorDialogShown = false
        )
    )
    val uiState: StateFlow<LoginState> = _uiState.asStateFlow()

    // Language change
    fun onLanguageChange() = viewModelScope.launch {
        _uiState.emit(_uiState.value.copy(currentLanguage = !_uiState.value.currentLanguage))
    }

    // Info Dialogs
    fun onUserInfoIconClicked() = viewModelScope.launch {
        _uiState.emit(_uiState.value.copy(isUserIdTextFieldDialogShown = true))
    }

    fun onUserDismissTextFieldDialog() = viewModelScope.launch {
        _uiState.emit(_uiState.value.copy(isUserIdTextFieldDialogShown = false))
    }

    fun onPassInfoIconClicked() = viewModelScope.launch {
        _uiState.emit(_uiState.value.copy(isPassIdTextFieldDialogShown = true))
    }

    fun onPassDismissTextFieldDialog() = viewModelScope.launch {
        _uiState.emit(_uiState.value.copy(isPassIdTextFieldDialogShown = false))
    }

    // Error Dialog
    fun showErrorDialog() = viewModelScope.launch {
        _uiState.emit(_uiState.value.copy(isErrorDialogShown = true))
    }

    fun onDismissErrorDialog() = viewModelScope.launch {
        _uiState.emit(
            _uiState.value.copy(
                isErrorDialogShown = false,
                errorBodyToShow = "",
                errorTitleToShow = ""
            )
        )
    }


    // Login Validation
    var loginFormState by mutableStateOf(LoginFormState())
    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    suspend fun getToken() {
        Log.d("ViewModel", "${LoginBody(_uiState.value.userId, _uiState.value.password)}")
        getUserTokenUseCase(LoginBody(_uiState.value.userId, _uiState.value.password))
            .collect { response ->
                Log.d("ViewModel", "response ${response.data}")
                    when (response) {
                    is Resource.Success -> {
                        viewModelScope.launch {
                            _uiState.emit(_uiState.value.copy(accessToken = response.data?.accessToken))
                        }
                    }
                    is Resource.Error -> {
                        Log.d("ViewModel", "getToken: ${response.message}")
                        invalidCredential()
                        showErrorDialog()
                    }

                    is Resource.Loading -> Log.d("ViewModel", "getToken: Loading")
                }
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
            setErrorMessage()
            viewModelScope.launch {
                showErrorDialog()
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


    // Error messages handler
    private fun setErrorMessage() {
        if (loginFormState.userIdError != null) {
            viewModelScope.launch {
                _uiState.emit(
                    _uiState.value.copy(
                        errorTitleToShow = "Wrong Inputs",
                        errorBodyToShow = StateConstants.langUiText[_uiState.value.currentLanguage]!!.userIdError[loginFormState.userIdError!!]
                    )
                )
            }
        } else if (loginFormState.passwordError != null) {
            viewModelScope.launch {
                _uiState.emit(
                    _uiState.value.copy(
                        errorTitleToShow = "Wrong Inputs",
                        errorBodyToShow = StateConstants.langUiText[_uiState.value.currentLanguage]!!.passError[loginFormState.passwordError!!]
                    )
                )
            }
        }
    }
    private fun invalidCredential() {
        viewModelScope.launch {
            _uiState.emit(
                _uiState.value.copy(
                    errorTitleToShow = StateConstants.langUiText[_uiState.value.currentLanguage]!!.invalidCredentials[0],
                    errorBodyToShow = StateConstants.langUiText[_uiState.value.currentLanguage]!!.invalidCredentials[1]
                )
            )
        }
    }
}



