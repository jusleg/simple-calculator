package com.simplemobiletools.calculator.operation

import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.github.kittinunf.fuel.httpGet

class CurrencyConversionOperation(var value: Double, var conversion_from: String,
                                           var conversion_to: String) {

    val dataString = "{\"base\":\"CAD\",\"date\":\"2018-03-05\",\"rates\":{\"AUD\":0.9983,\"BGN\":1.229,\"BRL\":2.5195,\"CHF\":0.72571,\"CNY\":4.9032,\"CZK\":15.963,\"DKK\":4.6794,\"EUR\":0.62838,\"GBP\":0.5597,\"HKD\":6.0574,\"HRK\":4.669,\"HUF\":197.16,\"IDR\":10643.0,\"ILS\":2.6756,\"INR\":50.351,\"ISK\":77.73,\"JPY\":81.702,\"KRW\":834.93,\"MXN\":14.615,\"MYR\":3.0199,\"NOK\":6.0535,\"NZD\":1.0699,\"PHP\":40.187,\"PLN\":2.6335,\"RON\":2.9294,\"RUB\":44.174,\"SEK\":6.3893,\"SGD\":1.0204,\"THB\":24.299,\"TRY\":2.9495,\"USD\":0.77334,\"ZAR\":9.1856}}"
    var json: JsonObject = Parser().parse(dataString!!.reader()) as JsonObject
    var rates = json.obj("rates")!!

    init {
        //Try to get rates from api fixer
        setRates()
    }

    internal fun setRates() {
        "https://api.fixer.io/latest?base=CAD".httpGet().responseString { request, response, result ->
            //handle response
            val (data, error) = result
            if (error == null) {
                //success
                json = Parser().parse(data!!.reader()) as JsonObject
                rates = json.obj("rates")!!
            } else {
                //error handling

            }
        }
    }

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