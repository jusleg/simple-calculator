package com.simplemobiletools.calculator.helpers

import android.os.AsyncTask
import android.util.Log
import com.simplemobiletools.calculator.activities.CryptoActivity
import com.simpletools.calculator.commons.helpers.Calculator
import com.simpletools.calculator.commons.helpers.Formatter
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class GetCryptoTask(val cryptoFROM: String, val cryptoTO: String, cryptoActivity: CryptoActivity, cryptoCalculatorImpl: CryptoCalculatorImpl) : AsyncTask<Void, Void, String>() {

    var mCalc: Calculator = cryptoActivity
    var cryptoCalculator: CryptoCalculatorImpl = cryptoCalculatorImpl

    override fun doInBackground(vararg params: Void?): String? {
        val url = URL("https://api.cryptonator.com/api/ticker/$cryptoFROM-$cryptoTO")

        val httpClient = url.openConnection() as HttpURLConnection

        if (httpClient.responseCode == HttpURLConnection.HTTP_OK) {
            try {
                val stream = BufferedInputStream(httpClient.inputStream)
                val data: String = readStream(inputStream = stream)
                return data
            } catch (e: Exception) {
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
        Log.e("result1", "result:"+result)
        var price: Double = 0.0
        try {
            price = JSONObject(result).getJSONObject("ticker").getDouble("price")
            Log.e("json1", "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% json object: " + JSONObject(result))

        } catch (ex: JSONException) {
            Log.e("exception","JSON exception")
        }
        cryptoCalculator.overwriteNumber(Formatter.stringToDouble(mCalc.getResult())* price)
    }
}