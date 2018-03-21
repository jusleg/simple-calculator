package com.simpletools.calculator.commons.helpers

interface Calculator {
    fun setValue(value: String)
    fun setFormula(value: String)
    fun setClear(value: String)
    fun getResult(): String
    fun getFormula(): String
    fun displayToast(message: String)
}
