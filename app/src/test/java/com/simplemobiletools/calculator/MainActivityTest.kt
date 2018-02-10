package com.simplemobiletools.calculator

import com.simplemobiletools.calculator.activities.MainActivity
import com.simplemobiletools.calculator.helpers.*
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.lang.reflect.Array.setDouble

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(21))
class MainActivityTest {
    private lateinit var activity: MainActivity

    private fun getDisplayedNumber() = activity.calc.displayedNumber

    @Before
    fun setUp() {
        activity = Robolectric.setupActivity(MainActivity::class.java)
    }

    @Test
    fun addSimpleDigit() {
        activity.calc.addDigit(2)
        assertEquals("2", getDisplayedNumber())
    }

    @Test
    fun removeLeadingZero() {
        activity.calc.addDigit(0)
        activity.calc.addDigit(5)
        assertEquals("5", getDisplayedNumber())
    }

    @Test
    fun additionTest() {
        activity.calc.addDigit(5)
        activity.calc.handleOperation("plus")
        activity.calc.addDigit(4)
        activity.calc.handleEquals()
        var result  = activity.getResult()

        assertEquals("9", result)
        //checkFormula("-1.2+3.4")
    }

    @Test
    fun subtractTest() {
        activity.calc.addDigit(5)
        activity.calc.handleOperation("minus")
        activity.calc.addDigit(4)
        activity.calc.handleEquals()
        var result  = activity.getResult()

        assertEquals("1", result)
        //checkFormula("-1.2+3.4")
    }

    @Test
    fun multiplyTest() {
        activity.calc.addDigit(5)
        activity.calc.handleOperation("multiply")
        activity.calc.addDigit(4)
        activity.calc.handleEquals()
        var result  = activity.getResult()

        assertEquals("20", result)
        //checkFormula("-1.2+3.4")
    }

    @Test
    fun divisionTest() {
        activity.calc.addDigit(6)
        activity.calc.handleOperation("divide")
        activity.calc.addDigit(2)
        activity.calc.handleEquals()
        var result  = activity.getResult()

        assertEquals("3", result)
    }

    @Test
    fun divisionByZero_returnsInfinity() {
        activity.calc.addDigit(6)
        activity.calc.handleOperation("divide")
        activity.calc.addDigit(0)
        activity.calc.handleEquals()
        var result  = activity.getResult()

        print("result: "+result)
        assertEquals("∞", result)
    }

    @Test
    fun percentageTest() {
        activity.calc.addDigit(3)
        activity.calc.handleOperation("percentage")
        activity.calc.handleEquals()
        var result  = activity.getResult()

        print("result: "+result)
        assertEquals("0.03", result)
    }

//    @Test
//    fun clearBtnSimpleTest() {
//        setDouble(156.0)
//        activity.calc.handleClear()
//        assertEquals("15", getDisplayedNumber())
//    }
//
//    @Test
//    fun clearBtnComplexTest() {
//        setDouble(-26.0)
//        activity.calc.handleClear()
//        assertEquals("-2", getDisplayedNumber())
//        activity.calc.handleClear()
//        assertEquals("0", getDisplayedNumber())
//    }

//    @Test
//    fun clearBtnLongClick_resetsEverything() {
//        calcResult(-1.2, PLUS, 3.4)
//        activity.calc.handleReset()
//        handleOperation(PLUS)
//        setDouble(3.0)
//        activity.calc.handleResult()
//        assertEquals("3", getDisplayedNumber())
//        checkFormula("")
//    }

//    @Test
//    fun complexTest() {
//        setDouble(-12.2)
//        handleOperation(PLUS)
//        setDouble(21.0)
//        handleOperation(MINUS)
//        assertEquals("8.8", getDisplayedNumber())
//        checkFormula("-12.2+21")
//
//        setDouble(1.6)
//        activity.calc.handleEquals()
//        assertEquals("7.2", getDisplayedNumber())
//        checkFormula("8.8-1.6")
//        activity.calc.handleEquals()
//        assertEquals("5.6", getDisplayedNumber())
//        checkFormula("7.2-1.6")
//
//        handleOperation(MULTIPLY)
//        setDouble(5.0)
//        handleOperation(DIVIDE)
//        assertEquals("28", getDisplayedNumber())
//        checkFormula("5.6*5")
//
//        setDouble(4.0)
//        handleOperation(MODULO)
//        assertEquals("7", getDisplayedNumber())
//        checkFormula("28/4")
//
//        activity.calc.handleClear()
//        assertEquals("0", getDisplayedNumber())
//    }

//    private fun setDouble(d: Double) {
//        activity.setValueDouble(d)
//    }

    private fun handleOperation(operation: String) {
        activity.calc.handleOperation(operation)
    }

    private fun checkFormula(desired: String) {
        assertEquals(desired, activity.calc.displayedFormula)
    }

//    private fun calcResult(baseValue: Double, operation: String, secondValue: Double): String? {
//        setDouble(baseValue)
//        handleOperation(operation)
//        setDouble(secondValue)
//        activity.calc.handleResult()
//        return getDisplayedNumber()
//    }
}
