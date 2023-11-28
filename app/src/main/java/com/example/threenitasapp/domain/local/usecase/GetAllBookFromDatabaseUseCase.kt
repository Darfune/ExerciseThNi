package com.example.threenitasapp.domain.local.usecase

import com.example.threenitasapp.domain.local.repository.BookDatabaseRepository
import javax.inject.Inject

class GetAllBookFromDatabaseUseCase @Inject constructor(
    private val repository: BookDatabaseRepository
) {
    operator fun invoke() = repository.getAllBooksFromDatabase()
}