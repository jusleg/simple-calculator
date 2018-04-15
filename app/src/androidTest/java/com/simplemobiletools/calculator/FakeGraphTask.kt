package com.simplemobiletools.calculator

import android.os.AsyncTask
import com.simplemobiletools.calculator.activities.DrawActivity

class FakeGraphTask( val drawActivity: DrawActivity, val status: Int) : AsyncTask<Void, Void, String>() {

    override fun doInBackground(vararg params: Void?): String? {
        when (status) {
            200 -> {
                return "\\sin(x)"
            }
            404 -> {
                pingWithError("Error 404!", "There has been a connection problem, returning you to the main page")
                return null
            }
            else -> {
                pingWithError("Error!", "There has been a connection problem, returning you to the main page")
                return null
            }
        }
    }

    fun pingWithError(title: String, message: String) {
        drawActivity.runOnUiThread(Runnable {
            drawActivity.recoverAfterError(title, message)
        })
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        if (result != null) {
            drawActivity.toggleWebView("file:///android_asset/test/succes.html")
        }
    }
}