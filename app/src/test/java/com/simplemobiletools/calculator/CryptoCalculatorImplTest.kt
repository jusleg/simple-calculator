package com.simplemobiletools.calculator

import com.simplemobiletools.calculator.activities.CryptoActivity
import com.simplemobiletools.calculator.helpers.CryptoCalculatorImpl
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = intArrayOf(21))
class CryptoCalculatorImplTest {
    private lateinit var activity: CryptoActivity
    private lateinit var calc: CryptoCalculatorImpl

    @Before
    fun setUp() {
        activity = Robolectric.setupActivity(CryptoActivity::class.java)
        calc = CryptoCalculatorImpl(activity, activity.applicationContext)
    }

    @Test
    fun addSimpleDigit() {
        calc.addDigit(1)
        calc.addDigit(2)
        calc.addDigit(3)
        Assert.assertEquals("123.000000", activity.getResult())
        calc.addDigit(4)
        Assert.assertEquals("1,234.000000", activity.getResult())
        calc.decimalClick()
        calc.addDigit(5)
        calc.addDigit(6)
        Assert.assertEquals("1,234.560000", activity.getResult())
        calc.addDigit(7)
        Assert.assertEquals("1,234.567000", activity.getResult())
    }

    @Test
    fun removeLeadingZero() {
        calc.addDigit(0)
        calc.addDigit(5)
        Assert.assertEquals("5.000000", activity.getResult())
    }

    @Test
    fun clearTest() {
        calc.addDigit(1)
        calc.addDigit(2)
        Assert.assertEquals("12.000000", activity.getResult())
        calc.handleClear()
        Assert.assertEquals("0.000000", activity.getResult())
    }

    @Test
    fun deleteTest() {
        calc.addDigit(1)
        calc.decimalClick()
        calc.addDigit(2)
        Assert.assertEquals("1.200000", activity.getResult())
        calc.handleDelete()
        Assert.assertEquals("1.000000", activity.getResult())
        calc.addDigit(3)
        Assert.assertEquals("13.000000", activity.getResult())
        calc.handleDelete()
        calc.handleDelete()
        Assert.assertEquals("0.000000", activity.getResult())
    }

    @Test
    fun deleteClearOnEmptyNumberTest() {
        calc.handleClear()
        Assert.assertEquals("0.000000", activity.getResult())
        calc.handleDelete()
        Assert.assertEquals("0.000000", activity.getResult())
    }
}
