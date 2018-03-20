package com.simplemobiletools.calculator.helpers

import android.content.Context
import android.net.ConnectivityManager
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.simplemobiletools.commons.extensions.toast
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class CurrencyRatesImpl(private val context: Context): CurrencyRates {

    private lateinit var ratesFile: File
    private lateinit var rates: JsonObject
    private val downloadRatesThread = Thread(Runnable {
        try {
            val url = "https://api.fixer.io/latest?base=CAD"
            val obj = URL(url)

            with(obj.openConnection() as HttpURLConnection) {
                // optional default is GET
                this.requestMethod = "GET"

                val data: String? = BufferedReader(InputStreamReader(inputStream)).use {
                    it.readText()
                }
                ratesFile = File(context.cacheDir, "rates")
                ratesFile.printWriter().use {
                    it.write(data)
                }

                rates = (Parser().parse(data!!.reader()) as JsonObject).obj("rates")!!
            }
        } catch(e: Exception) {
            e.printStackTrace()
        }
    })

    override fun updateCurrencyRates() {
        if (isConnected()) {
            downloadRatesThread.start()
        }
    }

    override fun get(symbol:String): Double {
        if (isEnable()) {
            return rates.get(symbol) as Double
        } else {
            context.toast("Need an internet connection to download currency rates")
            return 0.00
        }
    }

    private fun isConnected(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

    private fun isEnable(): Boolean {
        if (this::ratesFile.isInitialized) {
            if (!this::rates.isInitialized) {
                val data: String? = FileInputStream(ratesFile).bufferedReader().use { it.readText() }
                rates = (Parser().parse(data!!.reader()) as JsonObject).obj("rates")!!
            }
            return true
        } else if (isConnected()){
            updateCurrencyRates()
            downloadRatesThread.join()
            return true
        } else {
            return false
        }
    }
}