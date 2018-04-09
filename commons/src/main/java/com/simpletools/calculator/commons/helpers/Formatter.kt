package com.simpletools.calculator.commons.helpers

import java.lang.Math.abs
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

object Formatter {

    fun doubleToString(d: Double, parenthesize: Boolean): String {
        val symbols = DecimalFormatSymbols(Locale.US)
        symbols.decimalSeparator = '.'
        symbols.groupingSeparator = ','

        val formatter: DecimalFormat

        if (abs(d) >= Math.pow(10.0, 12.0) || abs(d) <= Math.pow(10.0, -12.0) && abs(d) != 0.0) {
            formatter = DecimalFormat("0.0E0", symbols)
        } else {
            formatter = DecimalFormat()
            formatter.decimalFormatSymbols = symbols
        }

        formatter.maximumFractionDigits = 12
        formatter.isGroupingUsed = true

        val str = formatter.format(d)
        if (parenthesize && d < 0) {
            return "($str)"
        }
        return str
    }

    fun doubleToStringWithGivenDigits(number: Double, digits: Int): String {
        return ("%,." + digits + "f").format(number)
    }

    fun stringToDouble(str: String) = str.replace(",", "").toDouble()
}
