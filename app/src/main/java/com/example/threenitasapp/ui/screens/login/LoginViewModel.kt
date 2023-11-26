package com.example.threenitasapp.ui.screens.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.threenitasapp.common.Constants
import com.example.threenitasapp.common.Resource
import com.example.threenitasapp.domain.models.LoginBody
import com.example.threenitasapp.domain.usecases.GetUserTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getUserTokenUseCase: GetUserTokenUseCase,
) : ViewModel() {

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

    fun checkUserIDContent(textFieldFlag: Boolean, userInput: String): Boolean =
        if (textFieldFlag) {
            userInput.matches(Constants.userIDCondition)
        } else {
            userInput.matches(Constants.lengthCondition) &&
                    userInput.matches(Constants.upperCaseLettersCondition) &&
                    userInput.matches(Constants.lowerCaseLettersCondition) &&
                    userInput.matches(Constants.digitsCondition) &&
                    userInput.matches(Constants.specialCharacterCondition)
        }


}