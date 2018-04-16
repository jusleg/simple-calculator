package com.simplemobiletools.calculator.helpers

import android.annotation.SuppressLint
import android.os.AsyncTask
import com.simplemobiletools.calculator.activities.DrawActivity
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

class GetGraphTask(val points: String, val drawActivity: DrawActivity) : AsyncTask<Void, Void, String>() {

    @SuppressLint("WrongConstant")
    override fun doInBackground(vararg params: Void?): String? {
        try {
            val url = URL("http://159.203.16.16/equation")

            val httpClient = url.openConnection() as HttpURLConnection
            httpClient.requestMethod = "POST"
            httpClient.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
            httpClient.setRequestProperty("charset", "utf-8")
            httpClient.useCaches = false

            val writer = OutputStreamWriter(httpClient.outputStream)
            writer.write("strokes=" + points)
            writer.flush()

            var data: String
            if (httpClient.responseCode == 200) {
                val stream = BufferedInputStream(httpClient.inputStream)
                data = readStream(inputStream = stream)
                httpClient.disconnect()
                return data
            } else if (httpClient.responseCode == 404) {
                httpClient.disconnect()
                pingWithError("Page not found 404!", "There has been a connection problem, returning you to the main page")
                killThread()
            } else if (httpClient.responseCode == 400) {
                httpClient.disconnect()
                pingWithError("Error 400!", "There has been a connection problem, returning you to the main page")
                killThread()
            }
        } catch (e: Exception) {
            pingWithError("Error!", "There has been a connection problem, returning you to the main page")
        }
        return null
    }

    fun pingWithError(title: String, message: String) {
        drawActivity.runOnUiThread(Runnable {
            drawActivity.recoverAfterError(title, message)
        })
    }

    fun killThread() {
        Thread.currentThread().interrupt()
    }

    fun readStream(inputStream: BufferedInputStream): String {
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val stringBuilder = StringBuilder()
        bufferedReader.forEachLine { stringBuilder.append(it) }
        return stringBuilder.toString()
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        if (result != null) {
            drawActivity.toggleWebView("http://m.wolframalpha.com/input/?i=" + result)
        }
    }
}