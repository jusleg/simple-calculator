package com.simpletools.calculator.commons

import com.simpletools.calculator.commons.helpers.Formatter
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(21))
class FormatterTest {

    @Test
    fun doubleToStringCommaTest() {
        assertEquals("1,234", Formatter.doubleToString(1234.0, false))
    }

    @Test
    fun doubleToStringCommaAndDecimalTest() {
        assertEquals("1,234.5", Formatter.doubleToString(1234.5, false))
    }

    @Test
    fun doubleToStringWithGivenDigitsTest() {
        assertEquals("1,234.500", Formatter.doubleToStringWithGivenDigits(1234.5, 3))
    }

    @Test
    fun stringToDoubleTest() {
        assertEquals(1234.5, Formatter.stringToDouble("1,234.5000"), 0.0000001)
    }

    @Test
    fun parenthesizeTest() {
        assertEquals("(-123.4)", Formatter.doubleToString(-123.4, true))
    }

    @Test
    fun scientificNotationTest1() {
        assertEquals("1.0E15", Formatter.doubleToString(1000000000000000.0, false))
    }

    @Test
    fun scientificNotationTest2() {
        assertEquals("1.0E-15", Formatter.doubleToString(0.000000000000001, false))
    }
}