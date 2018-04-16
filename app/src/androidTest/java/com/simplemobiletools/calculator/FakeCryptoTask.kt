package com.simplemobiletools.calculator

import android.os.AsyncTask
import android.util.Log
import com.simplemobiletools.calculator.activities.CryptoActivity
import com.simplemobiletools.calculator.helpers.CryptoCalculatorImpl
import com.simpletools.calculator.commons.helpers.Calculator
import com.simpletools.calculator.commons.helpers.Formatter
import org.json.JSONException
import org.json.JSONObject

class FakeCryptoTask(val cryptoFROM: String, val cryptoTO: String, cryptoActivity: CryptoActivity, cryptoCalculatorImpl: CryptoCalculatorImpl) : AsyncTask<Void, Void, String>() {

    var mCalc: Calculator = cryptoActivity
    var cryptoCalculator: CryptoCalculatorImpl = cryptoCalculatorImpl

    override fun doInBackground(vararg params: Void?): String? {
        return "{\n" +
                "    \"ticker\": {\n" +
                "        \"base\": \"BTC\",\n" +
                "        \"target\": \"ETH\",\n" +
                "        \"price\": \"16.385204\",\n" +
                "        \"volume\": \"\",\n" +
                "        \"change\": \"0.04874174\"\n" +
                "    },\n" +
                "    \"timestamp\": 1523775901,\n" +
                "    \"success\": true,\n" +
                "    \"error\": \"\"\n" +
                "}"
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
        cryptoCalculator.overwriteNumber(Formatter.stringToDouble(mCalc.getResult())* price)
    }
}