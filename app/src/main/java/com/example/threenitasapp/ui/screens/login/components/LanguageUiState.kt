package com.example.threenitasapp.ui.screens.login.components

import com.example.threenitasapp.R

object LanguageUiState{
    val english = UiText(
        topAppBarText = "Login",
        userText = "UserID",
        passText = "Password",
        showPassText = "Show",
        selectedLanguage = "English",
        languageIcon = R.drawable.ic_us_flag,
        buttonText = "Login"

    )
    val greek = UiText(
        topAppBarText = "Σύνδεση",
        userText = "UserID",
        passText = "Κωδικός",
        showPassText = "Προβολή",
        selectedLanguage = "Greek",
        languageIcon = R.drawable.ic_greek_flag,
        buttonText = "Σύνδεση"

    )
    val langUiText = hashMapOf<Boolean, UiText>(
        true to english,
        false to greek
    )
}
class UiText (
    val topAppBarText: String,
    val userText: String,
    val passText: String,
    val showPassText: String,
    val selectedLanguage: String,
    val languageIcon: Int,
    val buttonText: String
)