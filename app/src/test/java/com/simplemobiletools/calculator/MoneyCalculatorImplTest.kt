package com.simplemobiletools.calculator

import com.simplemobiletools.calculator.activities.MoneyActivity
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
        calc = MoneyCalculatorImpl(activity, activity.applicationContext)
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
    fun calculateTaxBritishColumbia(){
        calc.addDigit(2)
        Assert.assertEquals("2.00", activity.getResult())
        calc.addDigit(3)
        Assert.assertEquals("23.00", activity.getResult())
        calc.performTaxing("British Columbia")
        Assert.assertEquals("25.76", activity.getResult())
        calc.handleDelete()
        Assert.assertEquals("0.00", activity.getResult())
    }

    @Test
    fun calculateTaxAlberta(){
        calc.addDigit(2)
        Assert.assertEquals("2.00", activity.getResult())
        calc.addDigit(3)
        Assert.assertEquals("23.00", activity.getResult())
        calc.performTaxing("Alberta")
        Assert.assertEquals("24.15", activity.getResult())
        calc.handleDelete()
        Assert.assertEquals("0.00", activity.getResult())
    }

    @Test
    fun calculateTaxSaskatchewan(){
        calc.addDigit(2)
        Assert.assertEquals("2.00", activity.getResult())
        calc.addDigit(3)
        Assert.assertEquals("23.00", activity.getResult())
        calc.performTaxing("Saskatchewan")
        Assert.assertEquals("25.53", activity.getResult())
        calc.handleDelete()
        Assert.assertEquals("0.00", activity.getResult())
    }


    @Test
    fun calculateTaxManitoba(){
        calc.addDigit(2)
        Assert.assertEquals("2.00", activity.getResult())
        calc.addDigit(3)
        Assert.assertEquals("23.00", activity.getResult())
        calc.performTaxing("Manitoba")
        Assert.assertEquals("25.99", activity.getResult())
        calc.handleDelete()
        Assert.assertEquals("0.00", activity.getResult())
    }

    @Test
    fun calculateTaxOntario(){
        calc.addDigit(2)
        Assert.assertEquals("2.00", activity.getResult())
        calc.addDigit(3)
        Assert.assertEquals("23.00", activity.getResult())
        calc.performTaxing("Ontario")
        Assert.assertEquals("25.99", activity.getResult())
        calc.handleDelete()
        Assert.assertEquals("0.00", activity.getResult())
    }

    @Test
    fun calculateTaxQuebec(){
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
    fun calculateTaxNewBrunswick(){
        calc.addDigit(2)
        Assert.assertEquals("2.00", activity.getResult())
        calc.addDigit(3)
        Assert.assertEquals("23.00", activity.getResult())
        calc.performTaxing("New Brunswick")
        Assert.assertEquals("26.45", activity.getResult())
        calc.handleDelete()
        Assert.assertEquals("0.00", activity.getResult())
    }

    @Test
    fun calculateTaxNovaScotia(){
        calc.addDigit(2)
        Assert.assertEquals("2.00", activity.getResult())
        calc.addDigit(3)
        Assert.assertEquals("23.00", activity.getResult())
        calc.performTaxing("Nova Scotia")
        Assert.assertEquals("26.45", activity.getResult())
        calc.handleDelete()
        Assert.assertEquals("0.00", activity.getResult())
    }

    @Test
    fun calculateTaxPrinceEdwardIsland(){
        calc.addDigit(2)
        Assert.assertEquals("2.00", activity.getResult())
        calc.addDigit(3)
        Assert.assertEquals("23.00", activity.getResult())
        calc.performTaxing("Prince Edward Island")
        Assert.assertEquals("26.45", activity.getResult())
        calc.handleDelete()
        Assert.assertEquals("0.00", activity.getResult())
    }

    @Test
    fun calculateTaxNewfoundlandandLabrador(){
        calc.addDigit(2)
        Assert.assertEquals("2.00", activity.getResult())
        calc.addDigit(3)
        Assert.assertEquals("23.00", activity.getResult())
        calc.performTaxing("Newfoundland and Labrador")
        Assert.assertEquals("26.45", activity.getResult())
        calc.handleDelete()
        Assert.assertEquals("0.00", activity.getResult())
    }

    @Test
    fun calculateTaxNorthwestTerritories(){
        calc.addDigit(2)
        Assert.assertEquals("2.00", activity.getResult())
        calc.addDigit(3)
        Assert.assertEquals("23.00", activity.getResult())
        calc.performTaxing("Northwest Territories")
        Assert.assertEquals("24.15", activity.getResult())
        calc.handleDelete()
        Assert.assertEquals("0.00", activity.getResult())
    }

    @Test
    fun calculateTaxYukon(){
        calc.addDigit(2)
        Assert.assertEquals("2.00", activity.getResult())
        calc.addDigit(3)
        Assert.assertEquals("23.00", activity.getResult())
        calc.performTaxing("Yukon")
        Assert.assertEquals("24.15", activity.getResult())
        calc.handleDelete()
        Assert.assertEquals("0.00", activity.getResult())
    }

    @Test
    fun calculateTaxNunavut(){
        calc.addDigit(1)
        Assert.assertEquals("1.00", activity.getResult())
        calc.addDigit(0)
        Assert.assertEquals("10.00", activity.getResult())
        calc.performTaxing("Nunavut")
        Assert.assertEquals("10.50", activity.getResult())
        calc.handleDelete()
        Assert.assertEquals("0.00", activity.getResult())
    }

    @Test
    fun calculateTaxAtGeolocationTest() {

    }


}

