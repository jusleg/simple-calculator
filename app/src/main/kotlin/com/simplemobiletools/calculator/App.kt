package com.simplemobiletools.calculator

import android.app.Application
import com.simplemobiletools.commons.extensions.checkUseEnglish
import com.simplemobiletools.calculator.helpers.CurrencyRates

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        checkUseEnglish()
        CurrencyRates(this.applicationContext).updateCurrencyRates()
    }
}
