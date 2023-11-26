package com.example.threenitasapp

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class RegexTest {

    private val minLengthCondition = Regex("^.{8}$")
    private val upperCaseLettersCondition = Regex("(.*[A-Z]){2}")
    private val specialCharacterCondition = Regex("[!@#$%^&*()\\-_=+|{}\\[\\];:'\",<.>\\/?`~]{1}")
    private val digitsCondition = Regex("(.*[0-9]){2}")
    private val lowerCaseLettersCondition = Regex("(.*[a-z]){3}")

    val userIDCondition = Regex("^[A-Z]{2}\\d{4}\$")

    @Test
    fun testMatchingPassString() {
        val testString = "5TAs]0hg"
        assertTrue(minLengthCondition.matches(testString))
        assertTrue(upperCaseLettersCondition.containsMatchIn(testString))
        assertTrue(specialCharacterCondition.containsMatchIn(testString))
        assertTrue(digitsCondition.containsMatchIn(testString))
        assertTrue(lowerCaseLettersCondition.containsMatchIn(testString))
    }

    @Test
    fun testMatchingUserString() {
        val testString = "TH1234"
        assertTrue(userIDCondition.matches(testString))

    }

    @Test
    fun testNonMatchingString() {
        val testString = "invalidString"
        assertFalse(
            minLengthCondition.matches(testString) &&
                    upperCaseLettersCondition.matches(testString) &&
                    specialCharacterCondition.matches(testString) &&
                    digitsCondition.matches(testString) &&
                    lowerCaseLettersCondition.matches(testString)
        )
    }
}