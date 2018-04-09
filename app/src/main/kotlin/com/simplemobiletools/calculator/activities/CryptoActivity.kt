package com.simplemobiletools.calculator.activities

import android.annotation.SuppressLint
import android.os.Bundle
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
import android.widget.NumberPicker
import android.widget.Toast

class CryptoActivity : SimpleActivity(), Calculator {
    private var storedTextColor = 0
    private var vibrateOnButtonPress = true
    private var storedUseEnglish = false

    private var cryptoFROM: String = ""
    private var cryptoTO: String = ""

    lateinit var calc: MoneyCalculatorImpl

    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crypto)

        calc = MoneyCalculatorImpl(this, applicationContext)
        updateViewColors(crypto_holder, config.textColor)

        getButtonIds().forEach {
            it.setOnClickListener {
                calc.numpadClicked(it.id); checkHaptic(it)
            }
        }

        var numberPickerFROM: NumberPicker = numberPicker1
        var numberPickerTO: NumberPicker = numberPicker2

        // Values of each of the Number Pickers
        val valuesFROM = arrayOf("Bitcoin", "Ethereum", "Ripple", "Litecoin", "USD")
        val valuesTO = arrayOf("Ethereum", "Ripple", "Litecoin", "USD", "Bitcoin")

        // Setting default value of each Number Picker
        numberPickerFROM.minValue = 0
        numberPickerTO.minValue = 0

        // Setting size of each Number Picker
        numberPickerFROM.maxValue = valuesFROM.size - 1
        numberPickerTO.maxValue = valuesTO.size - 1

        // Setting Number Pickers to the values
        numberPickerFROM.displayedValues = valuesFROM
        numberPickerTO.displayedValues = valuesTO

        // Sets whether the selector wheel wraps when reaching the min/max value.
        numberPickerFROM.wrapSelectorWheel = true
        numberPickerTO.wrapSelectorWheel = true

        btn_convert_cryto.setOnClickListener { _ ->
            cryptoFROM = valuesFROM[numberPickerFROM.value]
            cryptoTO = valuesTO[numberPickerTO.value]

            var text = "val FROM: " + cryptoFROM + ", value TO: " + cryptoTO
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
            performConversion(cryptoFROM, cryptoTO)
        }
        btn_delete.setOnClickListener { calc.handleDelete(); checkHaptic(it) }
        btn_delete.setOnLongClickListener { calc.handleClear(); true }
        result.setOnLongClickListener { copyToClipboard(result.value); true }

        AutofitHelper.create(result)
    }

    private fun performConversion(cryptoFROM: String, cryptoTO: String) {
        print("")
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
//            updateButtonColor(config.customPrimaryColor)
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

    private fun getButtonIds() = arrayOf(btn_decimal, btn_0, btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9)
}
