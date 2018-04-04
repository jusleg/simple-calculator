package com.simplemobiletools.calculator.activities

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.view.View
import com.simplemobiletools.calculator.R
import com.simplemobiletools.calculator.helpers.MoneyCalculatorImpl
import com.simplemobiletools.commons.extensions.copyToClipboard
import com.simplemobiletools.commons.extensions.restartActivity
import com.simplemobiletools.commons.extensions.value
import com.simplemobiletools.commons.extensions.performHapticFeedback
import com.simplemobiletools.commons.extensions.toast
import com.simpletools.calculator.commons.activities.SimpleActivity
import com.simpletools.calculator.commons.extensions.config
import com.simpletools.calculator.commons.extensions.updateViewColors
import com.simpletools.calculator.commons.helpers.Calculator
import kotlinx.android.synthetic.main.activity_crypto.*
import me.grantland.widget.AutofitHelper

class CryptoActivity : SimpleActivity(), Calculator {
    private var storedTextColor = 0
    private var vibrateOnButtonPress = true
    private var storedUseEnglish = false

    lateinit var calc: MoneyCalculatorImpl

    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crypto)

        calc = MoneyCalculatorImpl(this, applicationContext)
        updateViewColors(crypto_holder, config.textColor)
        updateButtonColor(config.customPrimaryColor)

        getButtonIds().forEach {
            it.setOnClickListener {
                calc.numpadClicked(it.id); checkHaptic(it)
            }
        }

        btn_delete.setOnClickListener { calc.handleDelete(); checkHaptic(it) }
        btn_delete.setOnLongClickListener { calc.handleClear(); true }
        btn_btc2eth.setOnClickListener { true } // TODO : Implement feature and connect
        btn_btc2xrp.setOnClickListener { true } // TODO : Implement feature and connect
        btn_eth2btc.setOnClickListener { true } // TODO : Implement feature and connect
        btn_eth2xrp.setOnClickListener { true } // TODO : Implement feature and connect
        btn_xrp2btc.setOnClickListener { true } // TODO : Implement feature and connect
        btn_xrp2eth.setOnClickListener { true } // TODO : Implement feature and connect
        btn_usd2btc.setOnClickListener { true } // TODO : Implement feature and connect
        btn_usd2eth.setOnClickListener { true } // TODO : Implement feature and connect
        btn_usd2xrp.setOnClickListener { true } // TODO : Implement feature and connect
        result.setOnLongClickListener { copyToClipboard(result.value); true }

        AutofitHelper.create(result)
    }

    @SuppressLint("MissingSuperCall")
    override fun onResume() {
        super.onResume()
        if (storedUseEnglish != config.useEnglish) {
            restartActivity()
            return
        }

        if (storedTextColor != config.textColor) {
            updateViewColors(crypto_holder, config.textColor)
            updateButtonColor(config.customPrimaryColor)
        }
        vibrateOnButtonPress = config.vibrateOnButtonPress
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

    private fun checkHaptic(view: View) {
        if (vibrateOnButtonPress) {
            view.performHapticFeedback()
        }
    }

    private fun updateButtonColor(color: Int) {
        val states = arrayOf(intArrayOf(android.R.attr.state_enabled))
        val colors = intArrayOf(config.primaryColor)
        val myList = ColorStateList(states, colors)

        getCryptoButtonIds().forEach {
            it.setTextColor(getContrastColor(color))
            ViewCompat.setBackgroundTintList(it, myList)
        }
    }

    @SuppressLint("Range")
    private fun getContrastColor(color: Int): Int {
        val DARK_GREY = -13421773
        val y = (299 * Color.red(color) + 587 * Color.green(color) + 114 * Color.blue(color)) / 1000
        return if (y >= 149) DARK_GREY else Color.WHITE
    }

    private fun getCryptoButtonIds() = arrayOf(btn_btc2eth, btn_btc2xrp, btn_eth2btc, btn_eth2xrp, btn_xrp2btc, btn_xrp2eth, btn_usd2btc, btn_usd2eth, btn_usd2xrp)

    private fun getButtonIds() = arrayOf(btn_decimal, btn_0, btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9)
}
