package com.simplemobiletools.calculator.activities

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.res.ColorStateList
import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.view.View
import com.simplemobiletools.calculator.R
import com.simplemobiletools.calculator.helpers.ConversionCalculatorImpl
import com.simpletools.calculator.commons.extensions.config
import com.simpletools.calculator.commons.extensions.updateViewColors
import com.simpletools.calculator.commons.helpers.Calculator
import me.grantland.widget.AutofitHelper

/* ktlint-disable no-wildcard-imports */
import com.simplemobiletools.commons.extensions.*
import kotlinx.android.synthetic.main.activity_unit_conversion.*
import android.widget.Spinner
import android.widget.ArrayAdapter

class UnitConversionActivity : BaseActivity(), Calculator {

    lateinit var calc: ConversionCalculatorImpl

    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unit_conversion)

        calc = ConversionCalculatorImpl(this, applicationContext)
        updateViewColors(money_holder, config.textColor)
        updateButtonColor(config.customPrimaryColor)

        getButtonIds().forEach {
            it.setOnClickListener { calc.numpadClicked(it.id); checkHaptic(it) }
        }

        result.setOnLongClickListener { copyToClipboard(result.value); true }
        AutofitHelper.create(result)

        btn_delete.setOnClickListener { calc.handleDelete(); checkHaptic(it) }
        btn_delete.setOnLongClickListener { calc.handleClear(); true }

        btn_weight.setOnClickListener { displayDialog("weight", R.layout.weight_conversion_dialog,
                R.array.weight, R.id.from_weight_spinner, R.id.to_weight_spinner) }
        btn_length.setOnClickListener { displayDialog("length", R.layout.length_conversion_dialog,
                R.array.length, R.id.from_length_spinner, R.id.to_length_spinner) }
        btn_volume.setOnClickListener { displayDialog("volume", R.layout.volume_conversion_dialog,
                R.array.volume, R.id.from_volume_spinner, R.id.to_volume_spinner) }
    }

    @SuppressLint("MissingSuperCall")
    override fun onResume() {
        super.onResume()
        if (storedUseEnglish != config.useEnglish) {
            restartActivity()
            return
        }

        if (storedTextColor != config.textColor) {
            updateViewColors(money_holder, config.textColor)
            updateButtonColor(config.customPrimaryColor)
        }
        vibrateOnButtonPress = config.vibrateOnButtonPress
    }
    private fun updateButtonColor(color: Int) {
        val states = arrayOf(intArrayOf(android.R.attr.state_enabled))
        val colors = intArrayOf(config.primaryColor)
        val myList = ColorStateList(states, colors)

        getUnitConversionButtonIds().forEach {
            it.setTextColor(getContrastColor(color))
            ViewCompat.setBackgroundTintList(it, myList)
        }
    }

    private fun getUnitConversionButtonIds() = arrayOf(btn_weight, btn_length, btn_volume)
    private fun getButtonIds() = arrayOf(btn_decimal, btn_0, btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9)

    private fun displayDialog(type: String, layoutElem: Int, arrayElem: Int, fromSpinnerElem: Int, toSpinnerElem: Int) {

        val fromBuilder = AlertDialog.Builder(this@UnitConversionActivity)

        val view = layoutInflater.inflate(layoutElem, null)

        val fromSpinner = view.findViewById<View>(fromSpinnerElem) as Spinner
        val fromAdapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(this,
                arrayElem, android.R.layout.simple_spinner_item)
        fromAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        fromSpinner.adapter = fromAdapter

        val toSpinner = view.findViewById<View>(toSpinnerElem) as Spinner
        val toAdapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(this,
                arrayElem, android.R.layout.simple_spinner_item)
        toAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        toSpinner.adapter = toAdapter

        fromBuilder.setPositiveButton("Convert", { dialog, id ->
            val from = fromSpinner.selectedItem.toString()
            val to = toSpinner.selectedItem.toString()
            when (type) {
                "weight" -> calc.performWeightConversion(from, to)
                "length" -> calc.performLengthConversion(from, to)
                "volume" -> calc.performVolumeOperation(from, to)
            }
        })
                .setNegativeButton("Cancel", null)

        fromBuilder.setView(view)
        fromBuilder.show()
    }
}