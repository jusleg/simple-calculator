package com.simplemobiletools.calculator.activities

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.view.View
import com.simplemobiletools.calculator.R
import com.simplemobiletools.calculator.helpers.ConversionCalculatorImpl
import com.simpletools.calculator.commons.activities.SimpleActivity
import com.simpletools.calculator.commons.extensions.config
import com.simpletools.calculator.commons.extensions.updateViewColors
import com.simpletools.calculator.commons.helpers.Calculator
import me.grantland.widget.AutofitHelper

/* ktlint-disable no-wildcard-imports */
import com.simplemobiletools.commons.extensions.*
import kotlinx.android.synthetic.main.activity_unit_conversion.*
import android.widget.Spinner
import android.widget.ArrayAdapter

class UnitConversionActivity : SimpleActivity(), Calculator {
    private var storedTextColor = 0
    private var vibrateOnButtonPress = true
    private var storedUseEnglish = false

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

        btn_weight.setOnClickListener { displayWeightDialog() }
        btn_length.setOnClickListener { displayLengthDialog() }
        btn_volume.setOnClickListener { displayVolumeDialog() }
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

    override fun setValue(value: String) {
        result.text = value
    }

    override fun getResult(): String {
        return result.text.toString()
    }

    override fun setClear(text: String) {}
    override fun getFormula(): String { return "" }
    override fun setFormula(value: String) {}

    private fun checkHaptic(view: View) {
        if (vibrateOnButtonPress) {
            view.performHapticFeedback()
        }
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

    @SuppressLint("Range")
    private fun getContrastColor(color: Int): Int {
        val DARK_GREY = -13421773
        val y = (299 * Color.red(color) + 587 * Color.green(color) + 114 * Color.blue(color)) / 1000
        return if (y >= 149) DARK_GREY else Color.WHITE
    }

    private fun getUnitConversionButtonIds() = arrayOf(btn_weight, btn_length, btn_volume)
    private fun getButtonIds() = arrayOf(btn_decimal, btn_0, btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9)

    override fun displayToast(message: String) {
        applicationContext.toast(message, 100)
    }

    private fun displayWeightDialog() {

        val fromBuilder = AlertDialog.Builder(this@UnitConversionActivity)

        val view = layoutInflater.inflate(R.layout.weight_conversion_dialog, null)

        val fromSpinner = view.findViewById<View>(R.id.from_weight_spinner) as Spinner
        val fromAdapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(this,
                R.array.weight, android.R.layout.simple_spinner_item)
        fromAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        fromSpinner.adapter = fromAdapter

        val toSpinner = view.findViewById<View>(R.id.to_weight_spinner) as Spinner
        val toAdapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(this,
                R.array.weight, android.R.layout.simple_spinner_item)
        toAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        toSpinner.adapter = toAdapter

        fromBuilder.setPositiveButton("Convert", DialogInterface.OnClickListener { dialog, id ->
            val from = fromSpinner.selectedItem.toString()
            val to = toSpinner.selectedItem.toString()
            calc.performWeightConversion(from, to)
        })
                .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, id ->
                    // Cancel
                })

        fromBuilder.setView(view)
        fromBuilder.show()
    }

    private fun displayLengthDialog() {
        val fromBuilder = AlertDialog.Builder(this@UnitConversionActivity)

        val view = layoutInflater.inflate(R.layout.length_conversion_dialog, null)

        val fromSpinner = view.findViewById<View>(R.id.from_length_spinner) as Spinner
        val fromAdapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(this,
                R.array.length, android.R.layout.simple_spinner_item)
        fromAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        fromSpinner.adapter = fromAdapter

        val toSpinner = view.findViewById<View>(R.id.to_length_spinner) as Spinner
        val toAdapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(this,
                R.array.length, android.R.layout.simple_spinner_item)
        toAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        toSpinner.adapter = toAdapter

        fromBuilder.setPositiveButton("Convert", DialogInterface.OnClickListener { dialog, id ->
            val from = fromSpinner.selectedItem.toString()
            val to = toSpinner.selectedItem.toString()
            calc.performLengthConversion(from, to)
        })
                .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, id ->
                    // Cancel
                })

        fromBuilder.setView(view)
        fromBuilder.show()
    }

    private fun displayVolumeDialog() {
        val fromBuilder = AlertDialog.Builder(this@UnitConversionActivity)

        val view = layoutInflater.inflate(R.layout.volume_conversion_dialog, null)

        val fromSpinner = view.findViewById<View>(R.id.from_volume_spinner) as Spinner
        val fromAdapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(this,
                R.array.volume, android.R.layout.simple_spinner_item)
        fromAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        fromSpinner.adapter = fromAdapter

        val toSpinner = view.findViewById<View>(R.id.to_volume_spinner) as Spinner
        val toAdapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(this,
                R.array.volume, android.R.layout.simple_spinner_item)
        toAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        toSpinner.adapter = toAdapter

        fromBuilder.setPositiveButton("Convert", DialogInterface.OnClickListener { dialog, id ->
            val from = fromSpinner.selectedItem.toString()
            val to = toSpinner.selectedItem.toString()
            calc.performTempOperation(from, to)
        })
                .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, id ->
                    // Cancel
                })

        fromBuilder.setView(view)
        fromBuilder.show()
    }
}