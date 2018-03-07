package com.simplemobiletools.calculator.activities

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Spinner
import android.widget.Button
import com.simplemobiletools.calculator.R
import com.simplemobiletools.calculator.extensions.config
import com.simplemobiletools.calculator.extensions.updateViewColors
import com.simplemobiletools.calculator.helpers.Calculator
import com.simplemobiletools.calculator.helpers.CurrencyConverter
import com.simplemobiletools.calculator.helpers.MoneyCalculatorImpl
import com.simplemobiletools.calculator.helpers.TaxCalculator
import com.simplemobiletools.calculator.operation.TaxOperation
import com.simplemobiletools.commons.extensions.*
import kotlinx.android.synthetic.main.activity_money.*
import kotlinx.android.synthetic.main.tax_modal.view.*
import kotlinx.android.synthetic.main.currency_modal.view.*
import me.grantland.widget.AutofitHelper
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import android.app.ProgressDialog
import android.os.AsyncTask
import java.io.IOException
import java.net.MalformedURLException


class MoneyActivity : SimpleActivity(), Calculator , TaxCalculator, CurrencyConverter {

    private var storedTextColor = 0
    private var vibrateOnButtonPress = true
    private var storedUseEnglish = false
    private var taxDialog:AlertDialog.Builder? = null
    private var currencyDialog:AlertDialog.Builder? = null

    lateinit var calc: MoneyCalculatorImpl

    var pd: ProgressDialog? = null
    var conversionRatesJsonString : String = ""

    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_money)

        calc = MoneyCalculatorImpl(this, this, this, applicationContext)
        updateViewColors(money_holder, config.textColor)
        updateButtonColor(config.customPrimaryColor)

        getButtonIds().forEach {
            it.setOnClickListener { calc.numpadClicked(it.id); checkHaptic(it) }
        }

        btn_currency.setOnClickListener{ calc.calculateCurrencyConversion() } // TODO : Implement feature and connect
        btn_delete.setOnClickListener { calc.handleDelete(); checkHaptic(it) }
        btn_delete.setOnLongClickListener { calc.handleClear(); true }
        btn_taxes.setOnClickListener{ calc.calculateTax() } // TODO : Implement feature and connect
        btn_tip.setOnClickListener { true } // TODO : Implement feature and connect
        result.setOnLongClickListener { copyToClipboard(result.value); true }

        AutofitHelper.create(result)

        JsonTask().execute("https://api.fixer.io/latest?base=CAD")

    }

     override fun spawnTaxModal() {
        taxDialog = AlertDialog.Builder(this)
        val taxDialogView = layoutInflater.inflate(R.layout.tax_modal, null)
        taxDialog!!.setView(taxDialogView)
        taxDialog!!.setCancelable(true)
        var custom_dialog = taxDialog!!.create()
        custom_dialog.show()

        custom_dialog.findViewById<ListView>(R.id.province_selector_tax).setOnItemClickListener {
            adapterView: AdapterView<*>, view1: View, i: Int, l: Long ->
            var location = custom_dialog.findViewById<ListView>(R.id.province_selector_tax).getItemAtPosition(i).toString()
            custom_dialog.dismiss()
            calc.performTaxing(location)
        }
    }

    override fun spawnCurrencyModal() {
        currencyDialog = AlertDialog.Builder(this)
        val currencyDialogView = layoutInflater.inflate(R.layout.currency_modal, null)
        currencyDialog!!.setView(currencyDialogView)
        currencyDialog!!.setCancelable(true)
        var custom_dialog = currencyDialog!!.create()
        custom_dialog.show()
        var convert_from = "CAD"
        var convert_to = "CAD"

        custom_dialog.findViewById<Spinner>(R.id.convert_from).onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                convert_from = custom_dialog.findViewById<Spinner>(R.id.convert_from).getSelectedItem().toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        custom_dialog.findViewById<Spinner>(R.id.convert_to).onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                convert_to = custom_dialog.findViewById<Spinner>(R.id.convert_to).getSelectedItem().toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        custom_dialog.findViewById<Button>(R.id.convert).setOnClickListener {
            custom_dialog.dismiss()
            calc.performConversion(convert_from, convert_to)
        }
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

    override fun setValue(value: String, context: Context) {
        result.text = value
    }

    override fun getResult(): String {
        return result.text.toString()
    }

    override fun setClear(text: String){}
    override fun getFormula(): String { return "" }
    override fun setFormula(value: String, context: Context) {}

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


    private inner class JsonTask : AsyncTask<String, String, String>() {

        override fun onPreExecute() {
            super.onPreExecute()

            pd = ProgressDialog(this@MoneyActivity)
            pd?.setMessage("Please wait")
            pd?.setCancelable(false)
            pd?.show()
        }

        override fun doInBackground(vararg params: String): String {


            var connection: HttpURLConnection? = null
            var reader: BufferedReader? = null

            try {
                val url = URL(params[0])
                connection = url.openConnection() as HttpURLConnection
                connection.connect()


                val stream = connection.inputStream

                reader = BufferedReader(InputStreamReader(stream))

                val buffer = StringBuffer()
                var line: String? = null

                while (true) {
                    line = reader.readLine()
                    if (line == null) break
                    buffer.append(line + "\n")
                    Log.d("Response: ", "> $line")   //here u ll get whole response...... :-)

                }

                return buffer.toString()


            } catch (e: MalformedURLException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                if (connection != null) {
                    connection.disconnect()
                }
                try {
                    if (reader != null) {
                        reader.close()
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
            return ""
        }

        override fun onPostExecute(result: String) {
            super.onPostExecute(result)
            if (pd!!.isShowing()) {
                pd!!.dismiss()
            }
            conversionRatesJsonString = result
        }
    }
}


