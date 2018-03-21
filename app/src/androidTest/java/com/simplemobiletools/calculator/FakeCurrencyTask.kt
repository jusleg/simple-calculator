package com.simplemobiletools.calculator

import android.os.AsyncTask
import android.util.Log
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.simplemobiletools.calculator.helpers.MoneyCalculatorImpl
import com.simpletools.calculator.commons.helpers.Calculator
import com.simpletools.calculator.commons.helpers.Formatter

class FakeCurrencyTask(val from: String, val to: String, moneyActivity: Calculator, moneyCalculator: MoneyCalculatorImpl) : AsyncTask<Void, Void, String>() {

    var mCalc: Calculator = moneyActivity
    var moneyCalculator: MoneyCalculatorImpl = moneyCalculator

    override fun doInBackground(vararg params: Void?): String? {
        return "{\"result\":\"success\",\"timestamp\":1521579830,\"from\":\"CAD\",\"to\":\"USD\",\"rate\":0.76466751}"
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        var json = Parser().parse(result!!.reader()) as JsonObject
        Log.d("Conversion rate was:", json.double("rate").toString())
        var rate: Double? = json.double("rate")
        moneyCalculator.overwriteNumber(Formatter.stringToDouble(mCalc.getResult())* rate!!)
    }
}