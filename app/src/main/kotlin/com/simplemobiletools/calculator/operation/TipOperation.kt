package com.simplemobiletools.calculator.operation

/**
 * Created by IzKevin on 2018-03-04.
 */

class TipOperation(var value: Double, var tipPercentage: Double) {

    fun getResult(): Double {
        var tipMultiplier = tipPercentage + 1
        return value * tipMultiplier
    }
}