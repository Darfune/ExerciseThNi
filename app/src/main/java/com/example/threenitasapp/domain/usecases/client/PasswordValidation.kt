package com.example.threenitasapp.domain.usecases.client

import com.example.threenitasapp.common.Constants
import javax.inject.Inject

class PasswordValidation @Inject constructor(){
    operator fun invoke(password: String): ValidationResult {
        if (password.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password can't be blank"
            )
        }
        if (!password.matches(Constants.lengthCondition)) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password must be 8 characters long"
            )
        }
        if (!password.contains(Constants.upperCaseLettersCondition)) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password must contain 2 Upper case letters"
            )
        }
        if (!password.contains(Constants.lowerCaseLettersCondition)) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password must contain 3 lower case letters"
            )
        }
        if (!password.contains(Constants.digitsCondition)) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password must contain 2 digits"
            )
        }
        if (!password.contains(Constants.specialCharacterCondition)) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password must contain 1 special character"
            )
        }
        return ValidationResult(successful = true)
    }
}