package com.example.threenitasapp.ui.screens.login.components

import com.example.threenitasapp.R

object LanguageUiState {
    val english = UiText(
        topAppBarText = "Login",
        userText = "UserID",
        userDialogText = "must start with 2 capital letters and afterwards 4 numbers",
        passText = "Password",
        passDialogText = "at least 8 characters (2 uppercase, 3 lowercase, 1 special character, 2 numbers)",
        showPassText = "Show",
        selectedLanguage = "English",
        languageIcon = R.drawable.ic_us_flag,
        buttonText = "Login"

    )
    val greek = UiText(
        topAppBarText = "Σύνδεση",
        userText = "UserID",
        userDialogText = "πρέπει να ξεκινά με 2 κεφαλαίους χαρακτήρες και έπιτα να ακολουθάται από 4 αριθμούς",
        passText = "Κωδικός",
        passDialogText = "τουλάχιστον 8 χαρακτηρες (2 κεφαλαία, 3 πεζά, 1 ειδικός χαρακτήρας, 2 νούμερα)",
        showPassText = "Προβολή",
        selectedLanguage = "Greek",
        languageIcon = R.drawable.ic_greek_flag,
        buttonText = "Σύνδεση"

    )
    val langUiText = hashMapOf(
        true to english,
        false to greek
    )
}

class UiText(
    val topAppBarText: String,
    val userText: String,
    val userDialogText: String,
    val passText: String,
    val passDialogText: String,
    val showPassText: String,
    val selectedLanguage: String,
    val languageIcon: Int,
    val buttonText: String,
)