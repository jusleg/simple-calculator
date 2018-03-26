package com.simpletools.calculator.commons.helpers

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

object Formatter {
    fun doubleToString(d: Double): String {
        val symbols = DecimalFormatSymbols(Locale.US)
        symbols.decimalSeparator = '.'
        symbols.groupingSeparator = ','

        val formatter = DecimalFormat()
        formatter.maximumFractionDigits = 12
        formatter.decimalFormatSymbols = symbols
        formatter.isGroupingUsed = true
        return formatter.format(d)
    }

    fun doubleToStringWithGivenDigits(number: Double, digits: Int): String {
        return ("%,." + digits + "f").format(number)
    }

    fun stringToDouble(str: String) = str.replace(",", "").toDouble()
}
