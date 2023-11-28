package com.example.threenitasapp.data.remote.repository

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.example.threenitasapp.common.Resource
import com.example.threenitasapp.data.remote.BooksRemoteApi
import com.example.threenitasapp.data.remote.models.Book
import com.example.threenitasapp.data.remote.models.UserToken
import com.example.threenitasapp.domain.remote.model.LoginBody
import com.example.threenitasapp.domain.remote.repository.BooksRemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class BooksRemoteRepositoryImpl @Inject constructor(
    private val api: BooksRemoteApi,
) : BooksRemoteRepository {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun loginToApp(loginBody: LoginBody): Flow<Resource<UserToken>> =
        flow {
            try {
                emit(Resource.Loading())
                val token = api.loginToApp(loginBody)
                emit(Resource.Success(token))
            } catch (e: HttpException) {
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            } catch (e: IOException) {
                emit(Resource.Error("Couldn't reach server"))
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "An unexpected error occurred"))
            }
        }

    override suspend fun getBooks(token: String): Flow<Resource<List<Book>>> =
        flow {
            try {
                emit(Resource.Loading())
                val books = api.getBooks(token)
                emit(Resource.Success(books))
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "An unexpected error occurred"))
            }
        }

}