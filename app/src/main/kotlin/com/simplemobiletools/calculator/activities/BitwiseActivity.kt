package com.simplemobiletools.calculator.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import com.simplemobiletools.calculator.R
import com.simplemobiletools.calculator.helpers.BitwiseCalculatorImpl
import com.simpletools.calculator.commons.extensions.config
import com.simpletools.calculator.commons.extensions.updateViewColors

/* ktlint-disable no-wildcard-imports */
import com.simpletools.calculator.commons.helpers.*
import kotlinx.android.synthetic.main.activity_base.*
import com.simplemobiletools.commons.extensions.*
/* ktlint-enable no-wildcard-imports */

class BitwiseActivity : BaseActivity(), Calculator {

    private lateinit var currentBase: String
    private lateinit var calc: BitwiseCalculatorImpl

    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        calc = BitwiseCalculatorImpl(this, applicationContext)
        currentBase = DEC
        getButtonIds().forEach {
            it.setOnClickListener { calc.numpadClicked(it.id); checkHaptic(it) }
        }

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
        btn_clear.setOnLongClickListener { calc.handleReset(); true }
        formula.setOnLongClickListener { copyToClipboard(false) }
        result.setOnLongClickListener { copyToClipboard(true) }

        updateViewColors(base_holder, config.textColor)
        btn_dec.setTextColor(config.customPrimaryColor)
    }

    private fun enableButton(button: Button) {
        button.isEnabled = true
        button.setTextColor(config.textColor)
    }

    private fun disableButton(button: Button) {
        button.isEnabled = false
        button.setTextColor(config.backgroundColor.darkenColor())
    }

    private fun setBaseToDecimal() {
        calc.convertToDecimal(currentBase)
        currentBase = DEC

        btn_dec.isEnabled = false
        btn_dec.setTextColor(config.customPrimaryColor)

        enableButton(btn_oct)
        enableButton(btn_bin)

        enableButton(btn_2)
        enableButton(btn_3)
        enableButton(btn_4)
        enableButton(btn_5)
        enableButton(btn_6)
        enableButton(btn_7)
        enableButton(btn_8)
        enableButton(btn_9)
    }

    private fun setBaseToOctal() {
        calc.convertToOctal(currentBase)
        currentBase = OCT

        enableButton(btn_dec)

        btn_oct.isEnabled = false
        btn_oct.setTextColor(config.customPrimaryColor)

        enableButton(btn_bin)

        enableButton(btn_2)
        enableButton(btn_3)
        enableButton(btn_4)
        enableButton(btn_5)
        enableButton(btn_6)
        enableButton(btn_7)
        disableButton(btn_8)
        disableButton(btn_9)
    }

    private fun setBaseToBinary() {
        calc.convertToBinary(currentBase)
        currentBase = BIN

        enableButton(btn_dec)
        enableButton(btn_oct)

        btn_bin.isEnabled = false
        btn_bin.setTextColor(config.customPrimaryColor)

        disableButton(btn_2)
        disableButton(btn_3)
        disableButton(btn_4)
        disableButton(btn_5)
        disableButton(btn_6)
        disableButton(btn_7)
        disableButton(btn_8)
        disableButton(btn_9)
    }

    private fun getButtonIds() = arrayOf(btn_0, btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9)

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
}