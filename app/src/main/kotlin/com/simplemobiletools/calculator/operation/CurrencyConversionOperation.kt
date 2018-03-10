package com.simplemobiletools.calculator.operation

import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser

class CurrencyConversionOperation(var value: Double, var conversion_from: String,
                                           var conversion_to: String, conversionRatesJsonString: String) {

    var rates = (Parser().parse(conversionRatesJsonString!!.reader()) as JsonObject).obj("rates")!!

    internal fun getRate(): Double {
        if(conversion_from == conversion_to) {
            return 1.0
        } else if(conversion_from == "CAD") {
            return rates.get(conversion_to) as Double
        } else if(conversion_to == "CAD") {
            return (1.0 / (rates.get(conversion_from) as Double))
        } else {
            return ((1.0 / (rates.get(conversion_from) as Double)) *
                    (rates.get(conversion_to) as Double))
        }
    }

    fun getResult(): Double {
        return getRate() * value
    }

    fun getFormula(): String {
        return "Conversion rate from " + conversion_from + " to " + conversion_to + " is " + getRate()
    }


}