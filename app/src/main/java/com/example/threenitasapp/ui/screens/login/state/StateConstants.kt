package com.example.threenitasapp.ui.screens.login.state

import com.example.threenitasapp.R

object StateConstants {
    val english = UiText(
        topAppBarText = "Login",
        userText = "UserID",
        userDialogText = "must start with 2 capital letters and afterwards 4 numbers",
        passText = "Password",
        passDialogText = "at least 8 characters (2 uppercase, 3 lowercase, 1 special character, 2 numbers)",
        showPassText = "Show",
        hidePassText = "Hide",
        selectedLanguage = "English",
        languageIcon = R.drawable.ic_us_flag,
        buttonText = "Login",
        invalidInputs = "Not accepted data",
        userIdError = listOf("The UserID can't be blank", "The UserID is not valid"),
        passError = listOf(
            "The password can't be blank.",
            "The password must be 8 characters long.",
            "The password must contain 2 Upper case letters.",
            "The password must contain 3 lower case letters.",
            "The password must contain 2 digits.",
            "The password must contain 1 special character.",
        ),
        invalidCredentials = listOf(
            "Wrong Credentials",
            "You have submitted incorrect data."
        )

    )
    val greek = UiText(
        topAppBarText = "Σύνδεση",
        userText = "UserID",
        userDialogText = "πρέπει να ξεκινά με 2 κεφαλαίους χαρακτήρες και έπιτα να ακολουθάται από 4 αριθμούς",
        passText = "Κωδικός",
        passDialogText = "τουλάχιστον 8 χαρακτηρες (2 κεφαλαία, 3 πεζά, 1 ειδικός χαρακτήρας, 2 νούμερα)",
        showPassText = "Προβολή",
        hidePassText = "Απόκρυψη",
        selectedLanguage = "Greek",
        languageIcon = R.drawable.ic_greek_flag,
        buttonText = "Σύνδεση",
        invalidInputs = "Μη αποδεκτά στοιχεία",
        userIdError = listOf("Το UserID δεν μπορεί να είναι κενό", "Το UserID δεν είναι έγκυρο"),
        passError = listOf(
            "Ο κωδικός πρόσβασης δεν μπορεί να είναι κενός.",
            "Ο κωδικός πρόσβασης πρέπει να αποτελείται από 8 χαρακτήρες.",
            "Ο κωδικός πρόσβασης πρέπει να περιέχει 2 κεφαλαία γράμματα.",
            "Ο κωδικός πρόσβασης πρέπει να περιέχει 3 πεζά γράμματα.",
            "Ο κωδικός πρόσβασης πρέπει να περιέχει 2 ψηφία.",
            "Ο κωδικός πρόσβασης πρέπει να περιέχει 1 ειδικό χαρακτήρα.",
            "Έχετε υποβάλει λάθος στοιχεία."
        ),
        invalidCredentials = listOf(
            "Λανθασμένα στοιχεία",
            "Έχετε υποβάλει λάθος στοιχεία"
            )

    )

    val langUiText = hashMapOf(
        true to english,
        false to greek
    )

}