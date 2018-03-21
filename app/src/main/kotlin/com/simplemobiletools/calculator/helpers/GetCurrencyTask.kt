package com.simplemobiletools.calculator.helpers

import android.os.AsyncTask
import android.util.Log
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.simpletools.calculator.commons.helpers.Calculator
import com.simpletools.calculator.commons.helpers.Formatter
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class GetCurrencyTask(val from: String, val to: String, moneyActivity: Calculator, moneyCalculator: MoneyCalculatorImpl) : AsyncTask<Void, Void, String>() {

    var mCalc: Calculator = moneyActivity
    var moneyCalculator: MoneyCalculatorImpl = moneyCalculator

    override fun doInBackground(vararg params: Void?): String? {
        val url = URL("https://v3.exchangerate-api.com/pair/6d45cb0dc607b808bc2e7758/$from/$to")

        val httpClient = url.openConnection() as HttpURLConnection

        if(httpClient.responseCode == HttpURLConnection.HTTP_OK){
            try {
                val stream = BufferedInputStream(httpClient.inputStream)
                val data: String = readStream(inputStream = stream)
                return data
            } catch (e : Exception) {
                e.printStackTrace()
            } finally {
                httpClient.disconnect()
            }
        }
        return null
    }

    fun readStream(inputStream: BufferedInputStream): String {
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val stringBuilder = StringBuilder()
        bufferedReader.forEachLine { stringBuilder.append(it) }
        return stringBuilder.toString()
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        print(result)
        var json = Parser().parse(result!!.reader()) as JsonObject
        Log.d("Conversion rate was:", json.double("rate").toString())
        var rate: Double? = json.double("rate")
        moneyCalculator.overwriteNumber(Formatter.stringToDouble(mCalc.getResult())* rate!!)
    }
}