package com.example.threenitasapp.ui.screens.login.components

data class LoginFormState(
    val userId: String = "",
    val userIdError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
)
