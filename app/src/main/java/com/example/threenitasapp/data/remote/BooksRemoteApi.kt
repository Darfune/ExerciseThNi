package com.example.threenitasapp.data.remote

import com.example.threenitasapp.data.remote.models.usertoken.UserToken
import com.example.threenitasapp.domain.models.LoginBody
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.POST

interface BooksRemoteApi {

    @POST("Login")
    suspend fun loginToApp(
        @Body body: LoginBody
    ): UserToken

}