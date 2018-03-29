package com.simpletools.calculator.commons

import com.simpletools.calculator.commons.helpers.Calculator
import com.simpletools.calculator.commons.helpers.CalculatorImpl
import com.simpletools.calculator.commons.helpers.PLUS
import com.simpletools.calculator.commons.helpers.MINUS
import com.simpletools.calculator.commons.helpers.DIVIDE
import com.simpletools.calculator.commons.helpers.MULTIPLY
import com.simpletools.calculator.commons.helpers.PERCENTAGE
import com.simpletools.calculator.commons.helpers.POWER
import com.simpletools.calculator.commons.helpers.NEGATIVE
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(21))
class CalculatorImplTest {
    private lateinit var activity: Calculator
    private lateinit var calc: CalculatorImpl

    @Before
    fun setUp() {
        activity = FakeCalculator()
        calc = CalculatorImpl(activity)
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
        var result = activity.getResult()

        assertEquals("1", result)
        checkFormula("5−4")
    }

    @Test
    fun multiplyTest() {
        calc.addDigit(5)
        calc.handleOperation("multiply")
        calc.addDigit(4)
        calc.handleEquals()
        var result = activity.getResult()

        assertEquals("20", result)
        checkFormula("5×4")
    }

    @Test
    fun divisionTest() {
        calc.addDigit(6)
        calc.handleOperation("divide")
        calc.addDigit(2)
        calc.handleEquals()
        var result = activity.getResult()

        assertEquals("3", result)
    }

    @Test
    fun divisionByZero_returnsInfinity() {
        calc.addDigit(6)
        calc.handleOperation("divide")
        calc.addDigit(0)
        calc.handleEquals()
        var result = activity.getResult()

        assertEquals("∞", result)
    }

    @Test
    fun percentageTest() {
        calc.addDigit(8)
        calc.handleOperation("percentage")
        var result = activity.getResult()

        assertEquals("0.08", result)
        checkFormula("8%")
    }

    @Test
    fun powerTest() {
        calc.addDigit(3)
        calc.handleOperation("power")
        calc.addDigit(6)
        calc.handleEquals()
        var result = activity.getResult().toString()

        assertEquals("729", result)
        checkFormula("3^6")
    }

    @Test
    fun clearTest() {
        calc.addDigit(1)
        calc.handleOperation("plus")
        calc.addDigit(2)
        calc.handleClear()
        calc.addDigit(3)
        calc.handleEquals()
        var result = activity.getResult().toString()

        assertEquals("4", result)
    }

    @Test
    fun chainedEqualsUnaryOperationTest() {
        calc.addDigit(256)
        calc.handleOperation("root")
        calc.handleEquals() // No effect expected
        calc.handleEquals() // No effect expected
        calc.handleEquals() // No effect expected
        assertEquals("16", activity.getResult())
        checkFormula("√256")
    }

    @Test
    fun chainedEqualsBinaryOperationTest() {
        calc.addDigit(10)
        calc.handleOperation("plus")
        calc.addDigit(1)
        calc.handleEquals()     //11
        calc.handleEquals()     //12
        calc.handleEquals()     //13
        assertEquals("13", activity.getResult().toString())
        calc.handleOperation("minus")
        calc.addDigit(1)
        calc.handleEquals()     //12
        calc.handleEquals()     //11
        calc.handleEquals()     //10
        assertEquals("10", activity.getResult().toString())
        calc.handleOperation("multiply")
        calc.addDigit(2)
        calc.handleEquals()     //20
        calc.handleEquals()     //40
        calc.handleEquals()     //80
        assertEquals("80", activity.getResult().toString())
        calc.handleOperation("divide")
        calc.addDigit(2)
        calc.handleEquals()     //40
        calc.handleEquals()     //20
        calc.handleEquals()     //10
        assertEquals("10", activity.getResult().toString())
    }

    @Test
    fun chainedOperationRootTest() {
        calc.addDigit(1000)
        calc.handleOperation("plus")
        calc.addDigit(9000)
        calc.handleOperation("root")
        assertEquals("100", activity.getResult())
        checkFormula("√10,000")
        calc.handleOperation("root")
        assertEquals("10", activity.getResult())
        checkFormula("√100")
    }

    @Test
    fun chainedOperationPercentageTest() {
        calc.addDigit(1000)
        calc.handleOperation("plus")
        calc.addDigit(9000)
        calc.handleOperation("percentage")
        assertEquals("100", activity.getResult())
        checkFormula("10,000%")
        calc.handleOperation("percentage")
        assertEquals("1", activity.getResult())
        checkFormula("100%")
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

        calc.handleEquals() // Chained equals
        assertEquals("30", activity.getResult())
        checkFormula("31.6−1.6")

        handleOperation(MULTIPLY)
        setDouble(5.0)
        handleOperation(DIVIDE)
        assertEquals("150", activity.getResult())
        checkFormula("30×5")

        setDouble(4.0)
        handleOperation(POWER)
        assertEquals("37.5", activity.getResult())
        checkFormula("150÷4")

        setDouble(2.0)
        calc.handleEquals()
        assertEquals("1,406.25", activity.getResult())
        checkFormula("37.5^2")

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
            if (doubleString[letter].equals(".".single())) {
                calc.decimalClick()
            } else {
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
