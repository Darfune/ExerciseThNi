package com.example.threenitasapp.common

object Constants {
    val BASE_URL = "https://3nt-demo-backend.azurewebsites.net/Access/"

    val lengthCondition = Regex("^.{8}$")
    val upperCaseLettersCondition = Regex("(.*[A-Z]){2}")
    val specialCharacterCondition = Regex("[!@#$%^&*()\\-_=+|{}\\[\\];:'\",<.>\\/?`~]{1}")
    val digitsCondition = Regex("(.*[0-9]){2}")
    val lowerCaseLettersCondition = Regex("(.*[a-z]){3}")
    val userIDCondition = Regex("^[A-Z]{2}\\d{4}\$")

    val monthHashMap = hashMapOf<String, String>(
        "01" to "January",
        "02" to "February",
        "03" to "March",
        "04" to "April",
        "05" to "May",
        "06" to "June",
        "07" to "July",
        "08" to "August",
        "09" to "September",
        "10" to "October",
        "11" to "November",
        "12" to "December",
    )
}