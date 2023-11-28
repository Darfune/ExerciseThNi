package com.example.threenitasapp.domain.local.usecase

import com.example.threenitasapp.domain.local.repository.BookDatabaseRepository
import javax.inject.Inject

class GetBookByIdFromDatabaseUseCase @Inject constructor(
    private val repository: BookDatabaseRepository
) {
    suspend operator fun invoke(id: Int) = repository.getBookByIdFromDatabase(id)
}