package com.example.threenitasapp.ui.screens.login.state

data class LoginFormState(
    val userId: String = "",
    val userIdError: Int? = null,
    val password: String = "",
    val passwordError: Int? = null,
)
