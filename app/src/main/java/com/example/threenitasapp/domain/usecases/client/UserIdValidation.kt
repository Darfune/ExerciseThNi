package com.example.threenitasapp.domain.usecases.client

import com.example.threenitasapp.common.Constants
import com.example.threenitasapp.common.Resource
import com.example.threenitasapp.data.remote.models.usertoken.UserToken
import com.example.threenitasapp.domain.models.LoginBody
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserIdValidation @Inject constructor(){
    operator fun invoke(userId: String, languageFlag: Boolean? = null): ValidationResult {
        if (userId.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The UserID can't be blank"
            )
        }
        if (!userId.matches(Constants.userIDCondition)){
            return ValidationResult(
                successful = false,
                errorMessage = "The UserID is not valid"
            )
        }
        return ValidationResult(successful = true)
    }
}