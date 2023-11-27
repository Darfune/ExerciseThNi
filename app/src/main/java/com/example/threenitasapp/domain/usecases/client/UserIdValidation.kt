package com.example.threenitasapp.domain.usecases.client

import com.example.threenitasapp.common.Constants
import javax.inject.Inject

class UserIdValidation @Inject constructor(){
    operator fun invoke(userId: String): ValidationResult {
        if (userId.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = 0
            )
        }
        if (!userId.matches(Constants.userIDCondition)){
            return ValidationResult(
                successful = false,
                errorMessage = 1
            )
        }
        return ValidationResult(successful = true)
    }
}