package com.simplemobiletools.calculator.activities

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.view.View
import com.simplemobiletools.commons.extensions.performHapticFeedback
import com.simplemobiletools.commons.extensions.toast
import com.simpletools.calculator.commons.activities.SimpleActivity
import com.simpletools.calculator.commons.helpers.Calculator
import kotlinx.android.synthetic.main.activity_crypto.*

open class BaseActivity : SimpleActivity(), Calculator {
    var storedTextColor = 0
    var vibrateOnButtonPress = true
    var storedUseEnglish = false

    fun checkHaptic(view: View) {
        if (vibrateOnButtonPress) {
            view.performHapticFeedback()
        }
    }

    @SuppressLint("Range")
    fun getContrastColor(color: Int): Int {
        val DARK_GREY = -13421773
        val y = (299 * Color.red(color) + 587 * Color.green(color) + 114 * Color.blue(color)) / 1000
        return if (y >= 149) DARK_GREY else Color.WHITE
    }

    fun isOnline(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }

    override fun setValue(value: String) {
        result.text = value
    }

    override fun getResult(): String {
        return result.text.toString()
    }

    override fun setClear(text: String) {}

    override fun getFormula(): String { return "" }

    override fun setFormula(value: String) {}

    override fun displayToast(message: String) {
        applicationContext.toast(message, 100)
    }
}