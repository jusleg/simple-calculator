package com.simplemobiletools.calculator

import com.simplemobiletools.calculator.activities.UnitConversionActivity
import com.simplemobiletools.calculator.helpers.ConversionCalculatorImpl
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(21))
class ConversionCalculatorImplTest {
    private lateinit var activity: UnitConversionActivity
    private lateinit var calc: ConversionCalculatorImpl

    @Before
    fun setUp() {
        activity = Robolectric.setupActivity(UnitConversionActivity::class.java)
        calc = ConversionCalculatorImpl(activity, activity.applicationContext)
    }

    @Test
    fun addSimpleDigit() {
        calc.addDigit(1)
        calc.addDigit(2)
        calc.addDigit(3)
        Assert.assertEquals("123", activity.getResult())
        calc.addDigit(4)
        Assert.assertEquals("1234", activity.getResult())
        calc.decimalClick()
        calc.addDigit(5)
        calc.addDigit(6)
        Assert.assertEquals("1234.56", activity.getResult())
        calc.addDigit(7)
        Assert.assertEquals("1234.567", activity.getResult())
    }

    @Test
    fun removeLeadingZero() {
        calc.addDigit(0)
        calc.addDigit(5)
        Assert.assertEquals("5", activity.getResult())
    }

    @Test
    fun clearTest() {
        calc.addDigit(1)
        calc.addDigit(2)
        Assert.assertEquals("12", activity.getResult())
        calc.handleClear()
        Assert.assertEquals("0", activity.getResult())
    }

    @Test
    fun deleteTest() {
        calc.addDigit(1)
        calc.decimalClick()
        calc.addDigit(2)
        Assert.assertEquals("1.2", activity.getResult())
        calc.handleDelete()
        Assert.assertEquals("1", activity.getResult())
        calc.addDigit(3)
        Assert.assertEquals("13", activity.getResult())
        calc.handleDelete()
        calc.handleDelete()
        Assert.assertEquals("0", activity.getResult())
    }

    @Test
    fun deleteClearOnEmptyNumberTest() {
        calc.handleClear()
        Assert.assertEquals("0", activity.getResult())
        calc.handleDelete()
        Assert.assertEquals("0", activity.getResult())
    }

    @Test
    fun overwriteNumberTest() {
        calc.overwriteNumber(2139.2)
        Assert.assertEquals("2139.2", activity.getResult())
        calc.addDigit(2)
        Assert.assertEquals("2", activity.getResult())
        calc.overwriteNumber(10.4)
        Assert.assertEquals("10.4", activity.getResult())
        calc.handleDelete()
        Assert.assertEquals("0", activity.getResult())
    }

    // ------------------------------- Weight Conversion Tests -------------------------------

    @Test
    fun convertGramToKgBackAgain() {
        calc.addDigit(1)
        calc.addDigit(0)
        calc.addDigit(0)

        calc.performWeightConversion("g", "kg")
        Assert.assertEquals("0.1", activity.getResult())
        calc.performWeightConversion("kg", "g")
        Assert.assertEquals("100.0", activity.getResult())
    }

    @Test
    fun convertOzToLbBackAgain() {
        calc.addDigit(1)
        calc.addDigit(0)
        calc.addDigit(0)

        calc.performWeightConversion("oz", "lb")
        Assert.assertEquals("6.25", activity.getResult())
        calc.performWeightConversion("lb", "oz")
        Assert.assertEquals("100.0", activity.getResult())
    }

    @Test
    fun convertGramToOz() {
        calc.addDigit(1)
        calc.addDigit(0)
        calc.addDigit(0)

        calc.performWeightConversion("g", "oz")
        Assert.assertEquals("3.5274", activity.getResult())
    }

    @Test
    fun convertKgtoLb() {
        calc.addDigit(1)
        calc.addDigit(0)
        calc.addDigit(0)

        calc.performWeightConversion("kg", "lb")
        Assert.assertEquals("220.462", activity.getResult())
    }

    // ------------------------------- Length Conversion Tests -------------------------------

    @Test
    fun convertCmToMBackAgain() {
        calc.addDigit(1)
        calc.addDigit(0)
        calc.addDigit(0)

        calc.performLengthConversion("cm", "m")
        Assert.assertEquals("1.0", activity.getResult())
        calc.performLengthConversion("m", "cm")
        Assert.assertEquals("100.0", activity.getResult())
    }

    @Test
    fun convertFtToInBackAgain() {
        calc.addDigit(1)
        calc.addDigit(0)
        calc.addDigit(0)

        calc.performLengthConversion("ft", "in")
        Assert.assertEquals("1200.0", activity.getResult())
        calc.performLengthConversion("in", "ft")
        Assert.assertEquals("99.99996", activity.getResult())
    }

    @Test
    fun convertCmToIn() {
        calc.addDigit(1)
        calc.addDigit(0)
        calc.addDigit(0)

        calc.performLengthConversion("cm", "in")
        Assert.assertEquals("39.3701", activity.getResult())
    }

    @Test
    fun convertMtoFt() {
        calc.addDigit(1)
        calc.addDigit(0)
        calc.addDigit(0)

        calc.performLengthConversion("m", "ft")
        Assert.assertEquals("328.084", activity.getResult())
    }

// ------------------------------- Volume Conversion Tests -------------------------------

    @Test
    fun convertMlToLBackAgain() {
        calc.addDigit(1)
        calc.addDigit(0)
        calc.addDigit(0)

        calc.performVolumeOperation("Ml", "L")
        Assert.assertEquals("0.1", activity.getResult())
        calc.performVolumeOperation("L", "Ml")
        Assert.assertEquals("100.0", activity.getResult())
    }

    @Test
    fun convertQuartTogallonBackAgain() {
        calc.addDigit(1)
        calc.addDigit(0)
        calc.addDigit(0)

        calc.performVolumeOperation("Quart", "Gallon")
        Assert.assertEquals("25.0", activity.getResult())
        calc.performVolumeOperation("Gallon", "Quart")
        Assert.assertEquals("100.0", activity.getResult())
    }

    @Test
    fun convertMlToQuart() {
        calc.addDigit(1)
        calc.addDigit(0)

        calc.performVolumeOperation("Ml", "Quart")
        Assert.assertEquals("0.0105669", activity.getResult())
    }

    @Test
    fun convertLtoGallon() {
        calc.addDigit(1)
        calc.addDigit(0)
        calc.addDigit(0)

        calc.performVolumeOperation("L", "Gallon")
        Assert.assertEquals("26.4172", activity.getResult())
    }
}