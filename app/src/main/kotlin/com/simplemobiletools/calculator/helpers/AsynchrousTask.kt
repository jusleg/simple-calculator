package com.simplemobiletools.calculator.helpers

import android.os.AsyncTask
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStreamReader

open class AsynchrousTask : AsyncTask<Void, Void, String>() {

    override fun doInBackground(vararg params: Void?): String? {
        TODO("not implemented, to be overwritten by child classes")
    }

    fun readStream(inputStream: BufferedInputStream): String {
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val stringBuilder = StringBuilder()
        bufferedReader.forEachLine { stringBuilder.append(it) }
        return stringBuilder.toString()
    }
}