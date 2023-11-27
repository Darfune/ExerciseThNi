package com.example.threenitasapp.domain.usecases.remote

import com.example.threenitasapp.common.Resource
import com.example.threenitasapp.data.remote.models.UserToken
import com.example.threenitasapp.domain.models.remote.LoginBody
import com.example.threenitasapp.domain.repository.remote.BooksRemoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserTokenUseCase @Inject constructor(
    private val repo: BooksRemoteRepository
) {
    suspend operator fun invoke(loginBody: LoginBody): Flow<Resource<UserToken>> =
        repo.loginToApp(loginBody)
}