package com.simplemobiletools.calculator.helpers

import android.content.Context
import android.os.AsyncTask
import com.simplemobiletools.calculator.activities.MoneyActivity
import com.simpletools.calculator.commons.R
import com.simpletools.calculator.commons.helpers.Calculator
import com.simpletools.calculator.commons.helpers.Formatter
import com.simpletools.calculator.commons.operations.TaxOperation
import com.simpletools.calculator.commons.operations.TipOperation

class MoneyCalculatorImpl(calculator: Calculator, val context: Context) {
    private var mCallback: Calculator? = calculator
    private var number: String = ""
    private var decimalClicked: Boolean = false
    private var decimalCounter: Int = 0
    private var taskBuilder: BackgroundCurrencyTaskBuilder = BackgroundCurrencyTaskBuilder(calculator, this)

    init {
        setValue("0.00")
        handleClear()
    }

    fun numpadClicked(id: Int) {
        when (id) {
            R.id.btn_decimal -> decimalClick()
            R.id.btn_0 -> addDigit(0)
            R.id.btn_1 -> addDigit(1)
            R.id.btn_2 -> addDigit(2)
            R.id.btn_3 -> addDigit(3)
            R.id.btn_4 -> addDigit(4)
            R.id.btn_5 -> addDigit(5)
            R.id.btn_6 -> addDigit(6)
            R.id.btn_7 -> addDigit(7)
            R.id.btn_8 -> addDigit(8)
            R.id.btn_9 -> addDigit(9)
        }
    }

    fun addDigit(i: Int) {
        if ((number != "" || i != 0) && decimalCounter < 2) {
            number = number + i
            if (decimalClicked) {
                decimalCounter++
            }
            setValue(String.format("%,.2f", number.toDouble()))
        }
    }

    fun decimalClick() {
        if (number == "") {
            number = "0."
        } else if (!decimalClicked) {
            number += "."
        }
        decimalClicked = true
    }

    fun handleDelete() {
        if (number.length <= 1) {
            return handleClear()
        } else if (decimalClicked && decimalCounter<2) {
            number = number.dropLast(1 + decimalCounter)
            decimalClicked = false
            decimalCounter = 0
        } else if (decimalClicked) {
            decimalCounter--
            number = number.dropLast(1)
        } else {
            number = number.dropLast(1)
        }
        setValue(String.format("%,.2f", number.toDouble()))
    }

    fun handleClear() {
        number = ""
        decimalClicked = false
        decimalCounter = 0
        setValue("0.00")
    }

    // will be used by the 3 money action buttons to display the result
    fun overwriteNumber(newNumber: Double) {
        number = ""
        decimalClicked = false
        decimalCounter = 0
        setValue(String.format("%,.2f", newNumber))
    }

    private fun setValue(value: String) {
        mCallback!!.setValue(value)
    }

    fun performTaxing(location: String) {
        overwriteNumber(TaxOperation(getResult(), location).getResult())
    }

    private fun getResult() = Formatter.stringToDouble(mCallback!!.getResult())

    fun calculateTip(tip: Double) {
        if (isNumberValid()) {
            overwriteNumber(TipOperation(getResult(), tip).getResult())
        }
    }

    fun calcCurrency(from: String, to: String, moneyActivity: MoneyActivity) {
        var response: AsyncTask<Void, Void, String>? = taskBuilder.from(from).to(to).build().execute()
    }

    fun supersedeBuilder(builder: BackgroundCurrencyTaskBuilder) {
        taskBuilder = builder
    }

    private fun isNumberValid(): Boolean {
        if (getResult() > 0) {
            return true
        }
        return false
    }
}