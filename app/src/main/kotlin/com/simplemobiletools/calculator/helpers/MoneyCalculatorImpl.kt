package com.simplemobiletools.calculator.helpers

import android.content.Context
import android.os.AsyncTask
import com.simplemobiletools.calculator.activities.MoneyActivity
import com.simpletools.calculator.commons.helpers.Calculator
import com.simpletools.calculator.commons.operations.TaxOperation
import com.simpletools.calculator.commons.operations.TipOperation

class MoneyCalculatorImpl(calculator: Calculator, override val context: Context) : BaseCalculatorImpl(calculator, context) {
    private var taskBuilder: BackgroundCurrencyTaskBuilder = BackgroundCurrencyTaskBuilder(calculator, this)

    fun performTaxing(location: String) {
        overwriteNumber(TaxOperation.setParams(getResult(), location)!!.getResult())
    }

    fun calculateTip(tip: Double) {
        if (isNumberValid()) {
            overwriteNumber(TipOperation.setParams(getResult(), tip)!!.getResult())
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