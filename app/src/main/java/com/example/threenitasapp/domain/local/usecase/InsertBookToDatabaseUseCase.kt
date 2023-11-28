package com.example.threenitasapp.domain.local.usecase

import com.example.threenitasapp.data.local.models.BookEntity
import com.example.threenitasapp.domain.local.repository.BookDatabaseRepository
import javax.inject.Inject

class InsertBookToDatabaseUseCase @Inject constructor(
    private val repository: BookDatabaseRepository
) {
    suspend operator fun invoke(book: BookEntity) = repository.insertBookToDatabase(book)
}