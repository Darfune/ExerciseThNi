package com.example.threenitasapp.domain.repository

import com.example.threenitasapp.common.Resource
import com.example.threenitasapp.data.remote.models.usertoken.Book
import com.example.threenitasapp.data.remote.models.usertoken.UserToken
import com.example.threenitasapp.domain.models.LoginBody
import kotlinx.coroutines.flow.Flow

interface BooksRemoteRepository {
    suspend fun loginToApp(loginBody: LoginBody): Flow<Resource<UserToken>>
    suspend fun getBooks(token: String): Flow<Resource<List<Book>>>
}