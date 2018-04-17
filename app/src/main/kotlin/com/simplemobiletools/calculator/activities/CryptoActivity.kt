package com.simplemobiletools.calculator.activities

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import com.simplemobiletools.calculator.R
import com.simplemobiletools.commons.extensions.copyToClipboard
import com.simplemobiletools.commons.extensions.restartActivity
import com.simplemobiletools.commons.extensions.value
import com.simpletools.calculator.commons.extensions.config
import com.simpletools.calculator.commons.extensions.updateViewColors
import com.simpletools.calculator.commons.helpers.Calculator
import kotlinx.android.synthetic.main.activity_crypto.*
import me.grantland.widget.AutofitHelper
import android.widget.NumberPicker
import com.simplemobiletools.calculator.helpers.CryptoCalculatorImpl
import com.simpletools.calculator.commons.helpers.Formatter
import android.content.DialogInterface

class CryptoActivity : BaseActivity(), Calculator {

    private var cryptoFROM: String = ""
    private var cryptoTO: String = ""

    lateinit var calc: CryptoCalculatorImpl

    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crypto)

        calc = CryptoCalculatorImpl(this, applicationContext)
        updateViewColors(crypto_holder, config.textColor)

        getButtonIds().forEach {
            it.setOnClickListener {
                calc.numpadClicked(it.id); checkHaptic(it)
            }
        }

        var numberPickerFROM: NumberPicker = numberPicker1
        var numberPickerTO: NumberPicker = numberPicker2

        // Values of each of the Number Pickers
        val valuesFROM = resources.getStringArray(R.array.from_crypto_array)
        val valuesTO = resources.getStringArray(R.array.to_crypto_array)

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

        val cryptoSymbolHashMap = HashMap<String, String>()
        cryptoSymbolHashMap.put("Ethereum", "ETH")
        cryptoSymbolHashMap.put("Bitcoin", "BTC")
        cryptoSymbolHashMap.put("Ripple", "XRP")
        cryptoSymbolHashMap.put("Litecoin", "LTC")
        cryptoSymbolHashMap.put("USD", "USD")

        btn_convert_cryto.setOnClickListener { _ ->
            cryptoFROM = cryptoSymbolHashMap.get(valuesFROM[numberPickerFROM.value]).toString()
            cryptoTO = cryptoSymbolHashMap.get(valuesTO[numberPickerTO.value]).toString()

            if (cryptoTO == cryptoFROM) {
                calc.overwriteNumber(Formatter.stringToDouble(getResult()))
            } else {
                performConversion(cryptoFROM, cryptoTO)
            }
        }
        btn_delete.setOnClickListener { calc.handleDelete(); checkHaptic(it) }
        btn_delete.setOnLongClickListener { calc.handleClear(); true }
        result.setOnLongClickListener { copyToClipboard(result.value); true }

        AutofitHelper.create(result)
    }

    private fun performConversion(cryptoFROM: String, cryptoTO: String) {
        if (isOnline()) {
            calc.calculateCrypto(cryptoFROM, cryptoTO, this)
        } else if (!isOnline()) {
            displayToast("It seems there has been a connection problem, contact you ISP for more details !")
        }
    }

    fun displayErrorMessage(error: String, errorMessage: String) {
        findViewById<View>(R.id.loading).visibility = View.GONE
        val alertDialog = AlertDialog.Builder(this, R.style.MyDialogTheme).create()
        alertDialog.setTitle(error)
        alertDialog.setMessage(errorMessage)
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "OK",
                DialogInterface.OnClickListener { dialog, which -> dialog.dismiss() })
        alertDialog.show()
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
        }
        vibrateOnButtonPress = config.vibrateOnButtonPress
    }

    private fun getButtonIds() = arrayOf(btn_decimal, btn_0, btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9)
}
