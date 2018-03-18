package com.simplemobiletools.calculator.activities

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Spinner
import android.widget.Button
import com.simplemobiletools.calculator.R
import com.simplemobiletools.commons.extensions.*
import kotlinx.android.synthetic.main.activity_money.*
import me.grantland.widget.AutofitHelper
import android.app.ProgressDialog
import com.beust.klaxon.JsonObject
import com.simplemobiletools.calculator.helpers.*
import com.simpletools.calculator.commons.extensions.config
import com.simpletools.calculator.commons.extensions.updateViewColors
import com.simpletools.calculator.commons.helpers.Calculator
import com.simplemobiletools.calculator.helpers.MoneyCalculatorImpl
import android.content.DialogInterface
import com.simpletools.calculator.commons.activities.SimpleActivity


class MoneyActivity : SimpleActivity(), Calculator , MoneyCalculator {

    private var storedTextColor = 0
    private var vibrateOnButtonPress = true
    private var storedUseEnglish = false
    private var taxDialog:AlertDialog.Builder? = null

    lateinit var calc: MoneyCalculatorImpl
    lateinit var currencyRates: CurrencyRates

    var pd: ProgressDialog? = null

    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_money)

        calc = MoneyCalculatorImpl(this, this)
        updateViewColors(money_holder, config.textColor)
        updateButtonColor(config.customPrimaryColor)

        getButtonIds().forEach {
            it.setOnClickListener { calc.numpadClicked(it.id); checkHaptic(it) }
        }

        btn_currency.setOnClickListener{ displayCurrencyModal() }
        btn_delete.setOnClickListener { calc.handleDelete(); checkHaptic(it) }
        btn_delete.setOnLongClickListener { calc.handleClear(); true }
        btn_delete.setOnClickListener { calc.handleDelete(); checkHaptic(it) }
        btn_taxes.setOnClickListener{ calc.calculateTax() } // TODO : Implement feature and connect
        btn_tip.setOnClickListener { displayTipsDialog() }
        result.setOnLongClickListener { copyToClipboard(result.value); true }

        AutofitHelper.create(result)

        currencyRates = CurrencyRates(applicationContext)
        currencyRates.updateCurrencyRates()
    }

    override fun spawnTaxModal() {
        taxDialog = AlertDialog.Builder(this)
        val taxDialogView = layoutInflater.inflate(R.layout.tax_modal, null)
        taxDialog!!.setView(taxDialogView)
        taxDialog!!.setCancelable(true)
        var custom_dialog = taxDialog!!.create()
        custom_dialog.show()

        custom_dialog.findViewById<ListView>(R.id.province_selector_tax).setOnItemClickListener {
            _: AdapterView<*>, _: View, i: Int, _: Long ->
            var location = custom_dialog.findViewById<ListView>(R.id.province_selector_tax).getItemAtPosition(i).toString()
            custom_dialog.dismiss()
            calc.performTaxing(location)
        }
    }

    private fun displayCurrencyModal() {
        var currencyView = AlertDialog.Builder(this@MoneyActivity)
        currencyView!!.setView(layoutInflater.inflate(R.layout.currency_modal, null))
        currencyView!!.setCancelable(true)
        var currencyModal = currencyView!!.create()
        currencyModal.show()

        var convert_from = "CAD"
        var convert_to = "CAD"

        currencyModal.findViewById<Spinner>(R.id.convert_from).onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                convert_from = currencyModal.findViewById<Spinner>(R.id.convert_from).getSelectedItem().toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        currencyModal.findViewById<Spinner>(R.id.convert_to).onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                convert_to = currencyModal.findViewById<Spinner>(R.id.convert_to).getSelectedItem().toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        currencyModal.findViewById<Button>(R.id.convert).setOnClickListener {
            currencyModal.dismiss()
            calc.performConversion(convert_from, convert_to, currencyRates)
        }
    }

    override fun setValue(value: String) {
        result.text = value
    }

    override fun getResult(): String {
        return result.text.toString()
    }

    override fun setClear(text: String){}
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

        getMoneyButtonIds().forEach {
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

    private fun getMoneyButtonIds() = arrayOf(btn_tip, btn_currency, btn_taxes)
    private fun getButtonIds() = arrayOf(btn_decimal, btn_0, btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9)

    private fun displayTipsDialog() {
        val tipAmount = arrayOf("10%", "12%", "15%", "18%", "20%", "25%")

        val tipDialog = AlertDialog.Builder(this@MoneyActivity)
        tipDialog.setTitle("Calculate Tip")

        tipDialog.setItems(tipAmount, DialogInterface.OnClickListener { _, index ->
            var tipPercentage = tipAmount[index].trimEnd('%').toDouble() / 100
            calc.calculateTip(tipPercentage)
        })

        tipDialog.setNegativeButton("Cancel", DialogInterface.OnClickListener { _, _ ->
            // User cancelled the dialog
        })
        tipDialog.show()
    }
}
