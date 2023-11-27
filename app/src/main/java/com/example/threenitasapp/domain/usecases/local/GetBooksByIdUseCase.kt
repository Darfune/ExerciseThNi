package com.example.threenitasapp.domain.usecases.local

import com.example.threenitasapp.domain.repository.local.BooksDatabaseRepository
import javax.inject.Inject

class GetBooksByIdUseCase @Inject constructor(
    private val repository: BooksDatabaseRepository
) {
    operator fun invoke(id: Int) = repository.getBooksById(id)
}