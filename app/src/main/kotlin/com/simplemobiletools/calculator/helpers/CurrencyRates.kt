package com.simplemobiletools.calculator.helpers


interface CurrencyRates {
    fun get(symbol:String): Double
    fun updateCurrencyRates()
}