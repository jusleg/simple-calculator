package com.simplemobiletools.calculator.activities

import android.content.Intent
import com.simplemobiletools.commons.activities.BaseSplashActivity
import com.simplemobiletools.calculator.helpers.CurrencyRates

class SplashActivity : BaseSplashActivity() {
    override fun initActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        CurrencyRates(applicationContext).updateCurrencyRates()
        finish()
    }
}
