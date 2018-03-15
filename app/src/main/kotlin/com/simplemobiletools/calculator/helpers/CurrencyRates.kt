package com.simplemobiletools.calculator.helpers

import android.content.Context
import android.net.ConnectivityManager
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class CurrencyRates(private val context: Context) {

    private val ratesFile = File(context.cacheDir, "rates")

    fun updateCurrencyRates() {
        if (isConnected()) {
            val thread = Thread(Runnable {
                try {
                    val url = "https://api.fixer.io/latest?base=CAD"
                    val obj = URL(url)

                    with(obj.openConnection() as HttpURLConnection) {
                        // optional default is GET
                        this.requestMethod = "GET"

                        val data: String? = BufferedReader(InputStreamReader(inputStream)).use {
                            it.readText()
                        }

                        ratesFile.printWriter().use {
                            it.write(data)
                        }
                    }
                } catch(e: Exception) {
                    e.printStackTrace()
                }
            })

            thread.start()
        }
    }

    fun getCurrencyRates(): JsonObject {
        val data: String? = FileInputStream(ratesFile).bufferedReader().use { it.readText() }
        return (Parser().parse(data!!.reader()) as JsonObject).obj("rates")!!
    }

    private fun isConnected(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

}