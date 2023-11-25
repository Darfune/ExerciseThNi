package com.example.threenitasapp.domain.usecases

import com.example.threenitasapp.common.Resource
import com.example.threenitasapp.data.remote.models.usertoken.UserToken
import com.example.threenitasapp.domain.repository.BooksRemoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserTokenUseCase @Inject constructor(
    private val repo: BooksRemoteRepository
) {
    suspend operator fun invoke(userId: String, password: String): Flow<Resource<UserToken>> =
        repo.loginToApp(userId, password)
}