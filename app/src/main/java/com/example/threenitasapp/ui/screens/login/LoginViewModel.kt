package com.example.threenitasapp.ui.screens.login

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.threenitasapp.common.Resource
import com.example.threenitasapp.domain.models.LoginBody
import com.example.threenitasapp.domain.usecases.GetUserTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getUserTokenUseCase: GetUserTokenUseCase,
) : ViewModel() {

    suspend fun getToken(loginBody: LoginBody) =
        getUserTokenUseCase(loginBody)
            .collect { response ->
            Log.d("ViewModel", "getToken: Called")
            when (response){
                is Resource.Success -> Log.d("ViewModel","getToken: ${response.data}")
                is Resource.Error -> Log.d("ViewModel","getToken: ${response.message}")
                is Resource.Loading -> Log.d("ViewModel","getToken: Loading")
            }
        }
}