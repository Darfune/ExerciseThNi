package com.example.threenitasapp.ui.screens.login.state

data class LoginState(
    var userId: String,
    var password: String,
    var isUserIdTextFieldDialogShown: Boolean,
    var isPassIdTextFieldDialogShown: Boolean,
    var isErrorDialogShown: Boolean,
    var errorTitleToShow: String? = null,
    var errorBodyToShow: String? = null,
    val currentLanguage: Boolean,
    val accessToken: String? = null
)