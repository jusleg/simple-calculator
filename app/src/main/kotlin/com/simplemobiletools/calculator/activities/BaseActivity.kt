package com.simplemobiletools.calculator.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.simplemobiletools.calculator.R
import com.simplemobiletools.calculator.helpers.BaseCalculatorImpl
import com.simpletools.calculator.commons.activities.SimpleActivity
import com.simpletools.calculator.commons.extensions.config
import com.simpletools.calculator.commons.extensions.updateViewColors
import me.grantland.widget.AutofitHelper

/* ktlint-disable no-wildcard-imports */
import com.simpletools.calculator.commons.helpers.*
import kotlinx.android.synthetic.main.activity_base.*
import com.simplemobiletools.commons.extensions.*
/* ktlint-enable no-wildcard-imports */

class BaseActivity : SimpleActivity(), Calculator {

    private var storedTextColor = 0
    private var vibrateOnButtonPress = true
    private var storedUseEnglish = false

    lateinit var calc: BaseCalculatorImpl

    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        calc = BaseCalculatorImpl(this, applicationContext)

        getButtonIds().forEach {
            it.setOnClickListener { calc.numpadClicked(it.id); checkHaptic(it) }
        }
        btn_negative.setOnClickListener { calc.handleOperation(NEGATIVE); checkHaptic(it) }
        result.setOnLongClickListener { copyToClipboard(result.value); true }

        updateViewColors(base_holder, config.textColor)
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
            updateViewColors(base_holder, config.textColor)
        }
        vibrateOnButtonPress = config.vibrateOnButtonPress
    }

    private fun storeStateVariables() {
        config.apply {
            storedTextColor = textColor
            storedUseEnglish = useEnglish
        }
    }

    private fun checkHaptic(view: View) {
        if (vibrateOnButtonPress) {
            view.performHapticFeedback()
        }
    }

    private fun getButtonIds() = arrayOf(btn_0, btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9)

    override fun setValue(value: String) {
        result.text = value
    }

    override fun setFormula(value: String) {}

    override fun setClear(value: String) {}

    override fun getResult(): String {
        return result.text.toString()
    }

    override fun getFormula(): String { return "" }

    override fun displayToast(message: String) {
        applicationContext.toast(message, 100)
    }
}