package com.example.threenitasapp.domain.repository

import com.example.threenitasapp.common.Resource
import com.example.threenitasapp.data.remote.models.usertoken.UserToken
import kotlinx.coroutines.flow.Flow

interface BooksRemoteRepository {
    suspend fun loginToApp(userId: String, password: String): Flow<Resource<UserToken>>
}