package com.simplemobiletools.calculator.helpers

import android.os.AsyncTask
import com.simplemobiletools.calculator.activities.DrawActivity
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

class GetGraphTask(val points: String, val drawActivity: DrawActivity) : AsyncTask<Void, Void, String>() {

    override fun doInBackground(vararg params: Void?): String? {
        val url = URL("http://159.203.16.16/equation")

        val httpClient = url.openConnection() as HttpURLConnection

        httpClient.requestMethod = "POST"
        httpClient.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded")
        httpClient.setRequestProperty( "charset", "utf-8")
        httpClient.useCaches = false

        val writer = OutputStreamWriter(httpClient.outputStream)
        writer.write("strokes=" + points)
        writer.flush()

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
        } else {
            print("PROBLEM!")
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
        drawActivity.toggleWebView("http://m.wolframalpha.com/input/?i=" + result)
    }
}