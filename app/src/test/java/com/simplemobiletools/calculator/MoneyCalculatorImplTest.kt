package com.simplemobiletools.calculator

import com.simplemobiletools.calculator.activities.MoneyActivity
import com.simplemobiletools.calculator.helpers.CurrencyRates
import com.simplemobiletools.calculator.helpers.CurrencyRatesImpl
import com.simplemobiletools.calculator.helpers.FakeCurrencyRates
import com.simplemobiletools.calculator.helpers.MoneyCalculatorImpl
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(21))
class MoneyCalculatorImplTest {
    private lateinit var activity: MoneyActivity
    private lateinit var calc: MoneyCalculatorImpl

    @Before
    fun setUp() {
        activity = Robolectric.setupActivity(MoneyActivity::class.java)
        calc = MoneyCalculatorImpl(activity, activity)
    }

    @Test
    fun addSimpleDigit() {
        calc.addDigit(1)
        calc.addDigit(2)
        calc.addDigit(3)
        Assert.assertEquals("123.00", activity.getResult())
        calc.addDigit(4)
        Assert.assertEquals("1,234.00", activity.getResult())
        calc.decimalClick()
        calc.addDigit(5)
        calc.addDigit(6)
        Assert.assertEquals("1,234.56", activity.getResult())
        calc.addDigit(7)
        Assert.assertEquals("1,234.56", activity.getResult())
    }

    @Test
    fun removeLeadingZero() {
        calc.addDigit(0)
        calc.addDigit(5)
        Assert.assertEquals("5.00", activity.getResult())
    }

    @Test
    fun clearTest() {
        calc.addDigit(1)
        calc.addDigit(2)
        Assert.assertEquals("12.00", activity.getResult())
        calc.handleClear()
        Assert.assertEquals("0.00", activity.getResult())
    }

    @Test
    fun deleteTest() {
        calc.addDigit(1)
        calc.decimalClick()
        calc.addDigit(2)
        Assert.assertEquals("1.20", activity.getResult())
        calc.handleDelete()
        Assert.assertEquals("1.00", activity.getResult())
        calc.addDigit(3)
        Assert.assertEquals("13.00", activity.getResult())
        calc.handleDelete()
        calc.handleDelete()
        Assert.assertEquals("0.00", activity.getResult())
    }

    @Test
    fun deleteClearOnEmptyNumberTest() {
        calc.handleClear()
        Assert.assertEquals("0.00", activity.getResult())
        calc.handleDelete()
        Assert.assertEquals("0.00", activity.getResult())
    }

    @Test
    fun overwriteNumberTest(){
        calc.overwriteNumber(2139.2)
        Assert.assertEquals("2,139.20", activity.getResult())
        calc.addDigit(2)
        Assert.assertEquals("2.00", activity.getResult())
        calc.overwriteNumber(10.4)
        Assert.assertEquals("10.40", activity.getResult())
        calc.handleDelete()
        Assert.assertEquals("0.00", activity.getResult())
    }

    @Test
    fun calculateTax(){
        calc.addDigit(2)
        Assert.assertEquals("2.00", activity.getResult())
        calc.addDigit(3)
        Assert.assertEquals("23.00", activity.getResult())
        calc.performTaxing("Quebec")
        Assert.assertEquals("26.44", activity.getResult())
        calc.handleDelete()
        Assert.assertEquals("0.00", activity.getResult())
    }

    @Test
    fun calculateTip() {
        calc.handleDelete()
        calc.calculateTip(0.15)
        Assert.assertEquals("0.00", activity.getResult())

        calc.addDigit(1)
        calc.addDigit(0)
        calc.addDigit(0)
        calc.calculateTip(0.15)
        Assert.assertEquals("115.00", activity.getResult())

        calc.calculateTip(0.25)
        Assert.assertEquals("143.75", activity.getResult())
    }

    @Test
    fun calculateConversion() {
        var fakedCurrencyRate: CurrencyRates = FakeCurrencyRates(activity.applicationContext)
        calc.handleDelete()
        calc.performConversion("CAD", "USD", fakedCurrencyRate)
        Assert.assertEquals("0.00", activity.getResult())

        calc.addDigit(1)
        calc.addDigit(0)
        calc.addDigit(0)
        calc.performConversion("CAD", "USD", fakedCurrencyRate)
        Assert.assertEquals("130.00", activity.getResult())
    }
}
