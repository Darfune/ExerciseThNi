package com.example.threenitasapp.ui.screens.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.threenitasapp.common.Resource
import com.example.threenitasapp.domain.usecases.GetUserTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getUserTokenUseCase: GetUserTokenUseCase,
) : ViewModel() {
    init {
        Log.d("ViewModel", "Called: ")
        viewModelScope.launch {
            getToken("TH1234", "3NItas1!")
        }
    }

    suspend fun getToken(userId: String, password: String) =
        getUserTokenUseCase(userId, password)
            .collect { response ->
            Log.d("ViewModel", "getToken: Called")
            when (response){
                is Resource.Success -> Log.d("ViewModel","getToken: ${response.data}")
                is Resource.Error -> Log.d("ViewModel","getToken: ${response.message}")
                is Resource.Loading -> Log.d("ViewModel","getToken: Loading")
            }
        }
}