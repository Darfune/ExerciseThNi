package com.example.threenitasapp.domain.repository.remote

import com.example.threenitasapp.common.Resource
import com.example.threenitasapp.data.remote.models.Book
import com.example.threenitasapp.data.remote.models.UserToken
import com.example.threenitasapp.domain.models.remote.LoginBody
import kotlinx.coroutines.flow.Flow

interface BooksRemoteRepository {
    suspend fun loginToApp(loginBody: LoginBody): Flow<Resource<UserToken>>
    suspend fun getBooks(token: String): Flow<Resource<List<Book>>>
}