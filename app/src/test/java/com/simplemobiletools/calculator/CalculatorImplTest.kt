package com.simplemobiletools.calculator

import com.simplemobiletools.calculator.activities.MainActivity
import com.simplemobiletools.calculator.helpers.*
import org.junit.Assert.assertEquals
import kotlinx.android.synthetic.main.activity_main.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(21))
class CalculatorImplTest {
    private lateinit var activity: MainActivity
    private lateinit var calc: CalculatorImpl

    @Before
    fun setUp() {
        activity = Robolectric.setupActivity(MainActivity::class.java)
        calc = CalculatorImpl(activity, activity.applicationContext)
    }

    @Test
    fun addSimpleDigit() {
        calc.addDigit(2)
        assertEquals("2", activity.getResult())
    }

    @Test
    fun removeLeadingZero() {
        calc.addDigit(0)
        calc.addDigit(5)
        assertEquals("5", activity.getResult())
    }

    @Test
    fun additionTest() {
        calc.addDigit(5)
        calc.handleOperation("plus")
        calc.addDigit(4)
        calc.handleEquals()
        assertEquals("9", activity.getResult())
        checkFormula("5+4")
    }

    @Test
    fun subtractTest() {
        calc.addDigit(5)
        calc.handleOperation("minus")
        calc.addDigit(4)
        calc.handleEquals()
        var result  = activity.getResult()

        assertEquals("1", result)
        checkFormula("5−4")
    }

    @Test
    fun multiplyTest() {
        calc.addDigit(5)
        calc.handleOperation("multiply")
        calc.addDigit(4)
        calc.handleEquals()
        var result  = activity.getResult()

        assertEquals("20", result)
        checkFormula("5×4")
    }

    @Test
    fun divisionTest() {
        calc.addDigit(6)
        calc.handleOperation("divide")
        calc.addDigit(2)
        calc.handleEquals()
        var result  = activity.getResult()

        assertEquals("3", result)
    }

    @Test
    fun divisionByZero_returnsInfinity() {
        calc.addDigit(6)
        calc.handleOperation("divide")
        calc.addDigit(0)
        calc.handleEquals()
        var result  = activity.getResult()

        assertEquals("∞", result)
    }

    @Test
    fun percentageTest() {
        calc.addDigit(8)
        calc.handleOperation("percentage")
        var result  = activity.getResult()

        assertEquals("0.08", result)
        checkFormula("8%")
    }

    @Test
    fun powerTest() {
        calc.addDigit(3)
        calc.handleOperation("power")
        calc.addDigit(6)
        calc.handleEquals()
        var result  = activity.getResult().toString()

        assertEquals("729", result)
        checkFormula("3^6")
    }

    @Test
    fun clearTest() {
        calc.addDigit(1)
        calc.handleOperation("plus")
        calc.addDigit(2)
        calc.handleClear()
        assertEquals(activity.btn_clear.text,"AC")
        calc.addDigit(3)
        assertEquals(activity.btn_clear.text,"CE")
        calc.handleEquals()
        var result  = activity.getResult().toString()

        assertEquals("4", result)
    }

    @Test
    fun complexTest() {
        setDouble(12.2)
        handleOperation(PLUS)
        setDouble(21.0)
        handleOperation(MINUS)
        assertEquals("33.2", activity.getResult())
        checkFormula("12.2+21")

        setDouble(1.6)
        calc.handleEquals()
        assertEquals("31.6", activity.getResult())
        checkFormula("33.2−1.6")
        calc.handleEquals()

        handleOperation(MULTIPLY)
        setDouble(5.0)
        handleOperation(DIVIDE)
        assertEquals("158", activity.getResult())
        checkFormula("31.6×5")

        setDouble(4.0)
        handleOperation(POWER)
        assertEquals("39.5", activity.getResult())
        checkFormula("158÷4")

        setDouble(2.0)
        calc.handleEquals()
        assertEquals("1,560.25", activity.getResult())
        checkFormula("39.5^2")

        calc.handleClear()
        assertEquals("0", activity.getResult())

        setDouble(15.0)
        handleOperation(PERCENTAGE)
        assertEquals("0.15", activity.getResult())
        checkFormula("15%")
    }

    @Test
    fun negationTest() {
        setDouble(42.0)
        handleOperation(MULTIPLY)
        setDouble(2.0)
        handleOperation(NEGATIVE)
        handleOperation(MINUS)
        setDouble(4.0)
        handleOperation(NEGATIVE)
        calc.handleEquals()
        assertEquals("-80", activity.getResult())
        checkFormula("-84−-4")
    }

    private fun setDouble(d: Double) {
        var doubleString = d.toString()
        for (letter in doubleString.indices) {
           if(doubleString[letter].equals(".".single())){
                calc.decimalClick();
           }
            else{
               calc.addDigit(Integer.parseInt(doubleString[letter].toString()))
           }
        }

    }

    private fun handleOperation(operation: String) {
        calc.handleOperation(operation)
    }

    private fun checkFormula(desired: String) {
        assertEquals(desired, activity.getFormula())
    }

}
