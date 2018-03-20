package com.simplemobiletools.calculator.operation

import com.beust.klaxon.JsonObject

class CurrencyConversionOperation(var value: Double, var conversion_from: String,
                                           var conversion_to: String, var conversionRates: JsonObject) {

    internal fun getRate(): Double {
        if(conversion_from == conversion_to) {
            return 1.0
        } else if(conversion_from == "CAD") {
            return conversionRates.get(conversion_to) as Double
        } else if(conversion_to == "CAD") {
            return (1.0 / (conversionRates.get(conversion_from) as Double))
        } else {
            return ((1.0 / (conversionRates.get(conversion_from) as Double)) *
                    (conversionRates.get(conversion_to) as Double))
        }
    }

    fun getResult(): Double {
        return getRate() * value
    }

    fun getFormula(): String {
        return "Conversion rate from " + conversion_from + " to " + conversion_to + " is " + getRate()
    }


}