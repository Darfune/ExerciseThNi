package com.example.threenitasapp.domain.repository.local

import com.example.threenitasapp.data.local.models.Book
import kotlinx.coroutines.flow.Flow

interface BooksDatabaseRepository {
    fun getAllBooks(): Flow<List<Book>>

    fun getBooksById(id: Int): Flow<Book>

    suspend fun insertBook(book: Book)

    suspend fun updateBook(book: Book)

    suspend fun delete(id: Int)

}