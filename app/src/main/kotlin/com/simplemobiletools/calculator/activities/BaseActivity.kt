package com.simplemobiletools.calculator.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.simplemobiletools.calculator.R
import com.simplemobiletools.calculator.helpers.BaseCalculatorImpl
import com.simpletools.calculator.commons.activities.SimpleActivity
import com.simpletools.calculator.commons.extensions.config
import com.simpletools.calculator.commons.extensions.updateViewColors

/* ktlint-disable no-wildcard-imports */
import com.simpletools.calculator.commons.helpers.*
import kotlinx.android.synthetic.main.activity_base.*
import com.simplemobiletools.commons.extensions.*
/* ktlint-enable no-wildcard-imports */

class BaseActivity : SimpleActivity(), Calculator {

    private var vibrateOnButtonPress = true

    private lateinit var currentBase: String
    private lateinit var calc: BaseCalculatorImpl

    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        calc = BaseCalculatorImpl(this, applicationContext)
        currentBase = DEC

        setNumberButtonListeners()
        setOperationButtonListeners()
        setLongClickListeners()

        updateViewColors(base_holder, config.textColor)
        btn_dec.setTextColor(config.customPrimaryColor)
    }

    private fun setNumberButtonListeners() {
        arrayOf(btn_0, btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9).forEach {
            it.setOnClickListener { calc.numpadClicked(it.id); checkHaptic(it) }
        }
    }

    private fun setOperationButtonListeners() {
        btn_dec.setOnClickListener { setBaseToDecimal(); checkHaptic(it) }
        btn_oct.setOnClickListener { setBaseToOctal(); checkHaptic(it) }
        btn_bin.setOnClickListener { setBaseToBinary(); checkHaptic(it) }
        btn_negative.setOnClickListener { calc.handleOperation(NEGATIVE); checkHaptic(it) }
        btn_and.setOnClickListener { calc.handleOperation(AND); checkHaptic(it) }
        btn_or.setOnClickListener { calc.handleOperation(OR); checkHaptic(it) }
        btn_xor.setOnClickListener { calc.handleOperation(XOR); checkHaptic(it) }
        btn_inv.setOnClickListener { calc.handleOperation(INV); checkHaptic(it) }
        btn_equals.setOnClickListener { calc.handleEquals(); checkHaptic(it) }
        btn_clear.setOnClickListener { calc.handleClear(); checkHaptic(it) }
    }

    private fun setLongClickListeners() {
        btn_clear.setOnLongClickListener { calc.handleReset(); true }
        formula.setOnLongClickListener { copyToClipboard(false) }
        result.setOnLongClickListener { copyToClipboard(true) }
    }

    private fun checkHaptic(view: View) {
        if (vibrateOnButtonPress) {
            view.performHapticFeedback()
        }
    }

    private fun setBaseToDecimal() {
        calc.convertToDecimal(currentBase)
        currentBase = DEC
        btn_dec.setTextColor(config.customPrimaryColor)
        btn_dec.isEnabled = false
        btn_oct.setTextColor(config.textColor)
        btn_oct.isEnabled = true
        btn_bin.setTextColor(config.textColor)
        btn_bin.isEnabled = true
        toggleDecimalNumberButtons()
    }

    private fun toggleDecimalNumberButtons() {
        btn_2.isEnabled = true
        btn_3.isEnabled = true
        btn_4.isEnabled = true
        btn_5.isEnabled = true
        btn_6.isEnabled = true
        btn_7.isEnabled = true
        btn_8.isEnabled = true
        btn_9.isEnabled = true
    }

    private fun setBaseToOctal() {
        calc.convertToOctal(currentBase)
        currentBase = OCT
        btn_dec.setTextColor(config.textColor)
        btn_dec.isEnabled = true
        btn_oct.setTextColor(config.customPrimaryColor)
        btn_oct.isEnabled = false
        btn_bin.setTextColor(config.textColor)
        btn_bin.isEnabled = true
        toggleOctalNumberButtons()
    }

    private fun toggleOctalNumberButtons() {
        btn_2.isEnabled = true
        btn_3.isEnabled = true
        btn_4.isEnabled = true
        btn_5.isEnabled = true
        btn_6.isEnabled = true
        btn_7.isEnabled = true
        btn_8.isEnabled = false
        btn_9.isEnabled = false
    }


    private fun setBaseToBinary() {
        calc.convertToBinary(currentBase)
        currentBase = BIN
        btn_dec.setTextColor(config.textColor)
        btn_dec.isEnabled = true
        btn_oct.setTextColor(config.textColor)
        btn_oct.isEnabled = true
        btn_bin.setTextColor(config.customPrimaryColor)
        btn_bin.isEnabled = false
        toggleBinaryNumberButtons()
    }

    private fun toggleBinaryNumberButtons() {
        btn_2.isEnabled = false
        btn_3.isEnabled = false
        btn_4.isEnabled = false
        btn_5.isEnabled = false
        btn_6.isEnabled = false
        btn_7.isEnabled = false
        btn_8.isEnabled = false
        btn_9.isEnabled = false
    }

    private fun copyToClipboard(copyResult: Boolean): Boolean {
        var value = formula.value
        if (copyResult) {
            value = result.value
        }

        return if (value.isEmpty()) {
            false
        } else {
            copyToClipboard(value)
            true
        }
    }

    override fun setValue(value: String) {
        result.text = value
    }

    override fun setFormula(value: String) {
        formula.text = value
    }

    override fun setClear(text: String) {
        btn_clear.text = text
    }

    override fun getResult(): String {
        return result.text.toString()
    }

    override fun getFormula(): String {
        return formula.text.toString()
    }

    override fun displayToast(message: String) {
        applicationContext.toast(message, 100)
    }
}