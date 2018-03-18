package com.simplemobiletools.calculator.operation

import com.simplemobiletools.calculator.helpers.CurrencyRates

class CurrencyConversionOperation(var value: Double, var conversion_from: String,
                                           var conversion_to: String, var currencyRates: CurrencyRates) {

    internal fun getRate(): Double {
        if(conversion_from == conversion_to) {
            return 1.0
        } else if(conversion_from == "CAD") {
            return currencyRates.get(conversion_to)
        } else if(conversion_to == "CAD") {
            return (1.0 / currencyRates.get(conversion_from))
        } else {
            return ((1.0 / currencyRates.get(conversion_from)) *
                    currencyRates.get(conversion_to))
        }
    }

    fun getResult(): Double {
        return getRate() * value
    }

    fun getFormula(): String {
        return "Conversion rate from " + conversion_from + " to " + conversion_to + " is " + getRate()
    }


}