package com.example.threenitasapp.domain.usecases.local

import com.example.threenitasapp.domain.repository.local.BooksDatabaseRepository
import javax.inject.Inject

class DeleteBookUseCase @Inject constructor(
    private val repository: BooksDatabaseRepository
) {
    suspend operator fun invoke(id: Int) = repository.delete(id)
}