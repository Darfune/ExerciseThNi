package com.example.threenitasapp.domain.usecases.local

import com.example.threenitasapp.data.local.models.Book
import com.example.threenitasapp.domain.repository.local.BooksDatabaseRepository
import javax.inject.Inject

class InsertBooksUseCase @Inject constructor(
    private val repository: BooksDatabaseRepository
) {
    suspend operator fun invoke(book: Book) = repository.insertBook(book)
}