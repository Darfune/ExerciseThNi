package com.example.threenitasapp.ui.screens.login.state

sealed class LoginFormEvent {
    data class UserIdChanged(val userId: String): LoginFormEvent()
    data class PasswordChanged(val password: String): LoginFormEvent()

    object Submit: LoginFormEvent()
}