package com.example.threenitasapp.domain.client.usecase

data class ValidationResult(
   val successful: Boolean,
    val errorMessage: Int? = null
)
