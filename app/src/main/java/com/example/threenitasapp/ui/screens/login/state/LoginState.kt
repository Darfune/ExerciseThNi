package com.example.threenitasapp.ui.screens.login.state

data class LoginState(
    var userId: String,
    var password: String,
    var language: HashMap<Boolean, UiText> = StateConstants.langUiText
)