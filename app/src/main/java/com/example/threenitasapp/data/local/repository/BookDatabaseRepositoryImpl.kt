package com.example.threenitasapp.data.local.repository

import com.example.threenitasapp.data.local.BookDao
import com.example.threenitasapp.data.local.models.BookEntity
import com.example.threenitasapp.domain.local.repository.BookDatabaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookDatabaseRepositoryImpl @Inject constructor(
    private val dao: BookDao
) : BookDatabaseRepository {
    override fun getAllBooksFromDatabase(): Flow<List<BookEntity>> = dao.getAllBooks()
    override fun getBookByIdFromDatabase(id: Int): Flow<BookEntity>? = dao.getBookById(id)

    override suspend fun insertBookToDatabase(book: BookEntity) = dao.insertBook(book)

    override suspend fun deleteBookFromDatabase(id: Int) = dao.delete(id)
}