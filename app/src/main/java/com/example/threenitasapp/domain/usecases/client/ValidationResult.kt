package com.example.threenitasapp.domain.usecases.client

data class ValidationResult(
   val successful: Boolean,
    val errorMessage: Int? = null
)
