package com.simplemobiletools.calculator.helpers

import android.content.Context
import android.os.AsyncTask
import com.simplemobiletools.calculator.activities.CryptoActivity
import com.simpletools.calculator.commons.helpers.Calculator

class CryptoCalculatorImpl(calculator: Calculator, override val context: Context) : BaseCalculatorImpl(calculator, context) {
    private var taskBuilder: BackgroundCryptoTaskBuilder = BackgroundCryptoTaskBuilder(calculator as CryptoActivity, this)

    init {
        setValue("0.000000")
        handleClear()
    }

    override fun addDigit(i: Int) {
        if ((number != "" || i != 0) && decimalCounter < 5) {
            number = number + i
            if (decimalClicked) {
                decimalCounter++
            }
            setValue(String.format("%,.6f", number.toDouble()))
        }
    }

    override fun handleDelete() {
        if (number.length <= 1) {
            return handleClear()
        } else if (decimalClicked && decimalCounter<5) {
            number = number.dropLast(1 + decimalCounter)
            decimalClicked = false
            decimalCounter = 0
        } else if (decimalClicked) {
            decimalCounter--
            number = number.dropLast(1)
        } else {
            number = number.dropLast(1)
        }
        setValue(String.format("%,.6f", number.toDouble()))
    }

    override fun handleClear() {
        number = ""
        decimalClicked = false
        decimalCounter = 0
        setValue("0.000000")
    }

    // will be used by the 3 money action buttons to display the result
    override fun overwriteNumber(newNumber: Double) {
        number = ""
        decimalClicked = false
        decimalCounter = 0
        setValue(String.format("%,.6f", newNumber))
    }

    fun supersedeBuilder(builder: BackgroundCryptoTaskBuilder) {
        taskBuilder = builder
    }

    fun calculateCrypto(cryptoFROM: String, cryptoTO: String, cryptoActivity: CryptoActivity) {
        var response: AsyncTask<Void, Void, String>? = taskBuilder.from(cryptoFROM).to(cryptoTO).build().execute()
    }
}