package com.simplemobiletools.calculator.helpers

import android.util.Log
import com.simplemobiletools.calculator.activities.CryptoActivity
import com.simpletools.calculator.commons.helpers.Formatter
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedInputStream
import java.net.HttpURLConnection
import java.net.URL

class GetCryptoTask(val cryptoFROM: String, val cryptoTO: String, cryptoActivity: CryptoActivity, cryptoCalculatorImpl: CryptoCalculatorImpl) : AsynchrousTask() {

    private val cryptoActivity: CryptoActivity = cryptoActivity
    private var cryptoCalculator: CryptoCalculatorImpl = cryptoCalculatorImpl

    override fun doInBackground(vararg params: Void?): String? {
        try {
            val url = URL("https://api.cryptonator.com/api/ticker/$cryptoFROM-$cryptoTO")

            val httpClient = url.openConnection() as HttpURLConnection
            httpClient.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
            httpClient.setRequestProperty("charset", "utf-8")
            httpClient.useCaches = false

            if (httpClient.responseCode == HttpURLConnection.HTTP_OK) {
                val stream = BufferedInputStream(httpClient.inputStream)
                val data: String = readStream(inputStream = stream)
                httpClient.disconnect()
                return data
            } else {
                httpClient.disconnect()
                pingWithError("ERROR " + httpClient.responseCode, "There has been a connection problem, returning you to the main page")
                Thread.currentThread().interrupt()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun pingWithError(title: String, message: String) {
        cryptoActivity.runOnUiThread({
            cryptoActivity.displayErrorMessage(title, message)
        })
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        Log.e("result1", "result:" + result)
        var price: Double = 0.0
        try {
            price = JSONObject(result).getJSONObject("ticker").getDouble("price")
            Log.e("json1", "json object: " + JSONObject(result))
        } catch (ex: JSONException) {
            Log.e("exception", "JSON exception")
        }
        cryptoCalculator.overwriteNumber(Formatter.stringToDouble(cryptoActivity.getResult())* price)
    }
}