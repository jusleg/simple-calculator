package com.simpletools.calculator.wear

import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.view.View
import com.simplemobiletools.commons.extensions.performHapticFeedback
import com.simpletools.calculator.commons.extensions.config
import com.simpletools.calculator.commons.helpers.Calculator
import com.simpletools.calculator.commons.helpers.CalculatorImpl
import kotlinx.android.synthetic.main.activity_main.*
import me.grantland.widget.AutofitHelper


class MainActivity : WearableActivity(), Calculator {

    lateinit var calc: CalculatorImpl
    private var vibrateOnButtonPress = true

    override fun setValue(value: String) {
        result.text = value
    }

    override fun setFormula(value: String) {}

    override fun setClear(value: String) {
        btn_clear.text = value
    }

    override fun getResult(): String {
        return result.text.toString()
    }

    override fun getFormula(): String {
        return ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        calc = CalculatorImpl(this)
        vibrateOnButtonPress = config.vibrateOnButtonPress

        getButtonIds().forEach {
            it.setOnClickListener { calc.numpadClicked(it.id); checkHaptic(it) }
        }

        btn_clear.setOnClickListener { calc.handleClear(); checkHaptic(it) }
        btn_clear.setOnLongClickListener { calc.handleReset(); true }


        btn_equals.setOnClickListener { calc.handleEquals(); }

        AutofitHelper.create(result)
        // Enables Always-on
        setAmbientEnabled()
    }

    private fun getButtonIds() = arrayOf(btn_decimal, btn_0, btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9)

    private fun checkHaptic(view: View) {
        if (vibrateOnButtonPress) {
            view.performHapticFeedback()
        }
    }

}