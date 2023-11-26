package com.example.threenitasapp.common

object Constants {
    val BASE_URL = "https://3nt-demo-backend.azurewebsites.net/Access/"

    val lengthCondition = Regex("^.{8}$")
    val upperCaseLettersCondition = Regex("(.*[A-Z]){2}")
    val specialCharacterCondition = Regex("[!@#$%^&*()\\-_=+|{}\\[\\];:'\",<.>\\/?`~]{1}")
    val digitsCondition = Regex("(.*[0-9]){2}")
    val lowerCaseLettersCondition = Regex("(.*[a-z]){3}")
    val userIDCondition = Regex("^[A-Z]{2}\\d{4}\$")
}