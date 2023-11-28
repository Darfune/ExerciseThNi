package com.example.threenitasapp.domain.usecases.client

import com.example.threenitasapp.domain.client.usecase.PasswordValidation
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class PasswordValidationTest {

    private lateinit var validatePassword: PasswordValidation
    @Before
    fun setUp() {
        validatePassword = PasswordValidation()
    }

    @Test
    fun `Password is correct, validation succeeded`(){
        val result = validatePassword("3NItas1!")
        assert(result.successful)
    }
    @Test
    fun`Password is incorrect, because it's blank`(){
        val result = validatePassword("")
        assertEquals(result.errorMessage, "The password can't be blank")
    }

    @Test
    fun`Password is incorrect, length not 8`(){
        val result1 = validatePassword("3tas1!")
        assertEquals(result1.errorMessage, "The password must be 8 characters long")
        val result2 = validatePassword("3NIatas1!")
        assertEquals(result2.errorMessage, "The password must be 8 characters long")
    }

    @Test
    fun`Password is incorrect, not 2 upper case letters`(){
        val result = validatePassword("3nitas1!")
        assertEquals(result.errorMessage, "The password must contain 2 Upper case letters")
    }

    @Test
    fun`Password is incorrect, not 2 digits`(){
        val result = validatePassword("3NItass!")
        assertEquals(result.errorMessage, "The password must contain 2 digits")

    }

    @Test
    fun`Password is incorrect, not 3 lower case letter`(){
        val result = validatePassword("3NItaK4!")
        assertEquals(result.errorMessage, "The password must contain 3 lower case letters")
    }

    @Test
    fun`Password is incorrect, not 1 special character`(){
        val result = validatePassword("3NItas4o")
        assertEquals(result.errorMessage, "The password must contain 1 special character")
    }


}