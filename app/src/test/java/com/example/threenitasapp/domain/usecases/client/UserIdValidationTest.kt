package com.example.threenitasapp.domain.usecases.client

import com.example.threenitasapp.domain.client.usecase.UserIdValidation

import org.junit.Before
import org.junit.Test

class UserIdValidationTest {

    private lateinit var validateUserId: UserIdValidation
    @Before
    fun setUp() {
        validateUserId = UserIdValidation()
    }

    @Test
    fun `UserId is formatted correctly`(){
        val result = validateUserId("TH1234")
        assert(result.successful)
    }

    @Test
    fun `UserId is formatted incorrectly, more than 4 numbers`(){
        val result = validateUserId("TH12345")
        assert(!result.successful)
    }

    @Test
    fun `UserId is formatted incorrectly, doesn't begin with 4 upper case letters`(){
        val result = validateUserId("T123H")
        assert(!result.successful)
    }

    @Test
    fun `UserId is formatted incorrectly, has more than 2 upper case letters`(){
        val result = validateUserId("THV123")
        assert(!result.successful)
    }

}