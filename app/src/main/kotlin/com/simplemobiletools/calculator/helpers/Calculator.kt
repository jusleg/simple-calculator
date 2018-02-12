package com.simplemobiletools.calculator.helpers

import android.content.Context

interface Calculator {
    fun setValue(value: String, context: Context)

    fun setFormula(value: String, context: Context)
    fun setClear(value: String)
    fun getResult(): String
    fun getFormula(): String
}
