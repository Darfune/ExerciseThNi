package com.example.threenitasapp.domain.usecases.remote

import com.example.threenitasapp.common.Resource
import com.example.threenitasapp.data.remote.models.Book
import com.example.threenitasapp.domain.repository.remote.BooksRemoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBooksUseCase @Inject constructor(
    private val repo: BooksRemoteRepository
) {
    suspend operator fun invoke(token: String): Flow<Resource<List<Book>>> =
        repo.getBooks(token)
}