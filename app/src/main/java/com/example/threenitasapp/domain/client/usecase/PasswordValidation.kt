package com.example.threenitasapp.domain.client.usecase

import com.example.threenitasapp.common.Constants
import javax.inject.Inject

class PasswordValidation @Inject constructor(){
    operator fun invoke(password: String): ValidationResult {
        if (password.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = 0
            )
        }
        if (!password.matches(Constants.lengthCondition)) {
            return ValidationResult(
                successful = false,
                errorMessage = 1
            )
        }
        if (!password.contains(Constants.upperCaseLettersCondition)) {
            return ValidationResult(
                successful = false,
                errorMessage = 2
            )
        }
        if (!password.contains(Constants.lowerCaseLettersCondition)) {
            return ValidationResult(
                successful = false,
                errorMessage = 3
            )
        }
        if (!password.contains(Constants.digitsCondition)) {
            return ValidationResult(
                successful = false,
                errorMessage = 4
            )
        }
        if (!password.contains(Constants.specialCharacterCondition)) {
            return ValidationResult(
                successful = false,
                errorMessage = 5
            )
        }
        return ValidationResult(successful = true)
    }
}