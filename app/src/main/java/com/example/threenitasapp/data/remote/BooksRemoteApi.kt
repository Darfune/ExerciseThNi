package com.example.threenitasapp.data.remote

import com.example.threenitasapp.data.remote.models.Book
import com.example.threenitasapp.data.remote.models.UserToken
import com.example.threenitasapp.domain.models.remote.LoginBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface BooksRemoteApi {

    @POST("Login")
    suspend fun loginToApp(
        @Body body: LoginBody
    ): UserToken

    @GET("Books")
    suspend fun getBooks(@Header("Authorization") token: String): List<Book>

}