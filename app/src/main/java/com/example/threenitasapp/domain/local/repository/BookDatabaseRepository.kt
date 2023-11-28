package com.example.threenitasapp.domain.local.repository

import com.example.threenitasapp.data.local.models.BookEntity
import kotlinx.coroutines.flow.Flow

interface BookDatabaseRepository {
    fun getAllBooksFromDatabase(): Flow<List<BookEntity>>
    fun getBookByIdFromDatabase(id: Int): Flow<BookEntity>?

    suspend fun insertBookToDatabase(book: BookEntity)

    suspend fun deleteBookFromDatabase(id: Int)
}