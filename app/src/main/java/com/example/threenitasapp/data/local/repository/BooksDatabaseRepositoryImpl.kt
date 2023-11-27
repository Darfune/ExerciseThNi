package com.example.threenitasapp.data.local.repository

import com.example.threenitasapp.data.local.BookDao
import com.example.threenitasapp.data.local.models.Book
import com.example.threenitasapp.domain.repository.local.BooksDatabaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BooksDatabaseRepositoryImpl @Inject constructor(private val bookDao: BookDao) :
    BooksDatabaseRepository {
    override fun getAllBooks(): Flow<List<Book>> = bookDao.getAllBooks()

    override fun getBooksById(id: Int): Flow<Book> = bookDao.getBookById(id)

    override suspend fun insertBook(book: Book) = bookDao.insertBook(book)
    override suspend fun updateBook(book: Book) = bookDao.updateBook(book)

    override suspend fun delete(id: Int) = bookDao.delete(id)
}
