package com.example.threenitasapp.domain.models


import com.google.gson.annotations.SerializedName


data class LoginBody(
    @SerializedName("UserName")
    val userName: String,
    @SerializedName("Password")
    val password: String,
)