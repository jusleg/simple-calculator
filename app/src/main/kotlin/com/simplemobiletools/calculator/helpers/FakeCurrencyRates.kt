package com.simplemobiletools.calculator.helpers

import android.content.Context

class FakeCurrencyRates(private val context: Context): CurrencyRates {

    override fun get(symbol:String): Double {
        return 1.3
    }

    override fun updateCurrencyRates() {

    }
}