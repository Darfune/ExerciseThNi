package com.example.threenitasapp.data.remote.models.usertoken


import com.google.gson.annotations.SerializedName


data class UserToken(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("expires_in")
    val expiresIn: Int,
    @SerializedName("refresh_token")
    val refreshToken: String,
    @SerializedName("token_type")
    val tokenType: String
)