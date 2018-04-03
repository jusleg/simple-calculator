package com.simpletools.calculator.commons.operations

object TipOperation {
    var value: Double = 0.0
    var tipPercentage: Double = 0.0

    fun setParams(value: Double, tipPercentage: Double): TipOperation? {
        this.value = value
        this.tipPercentage = tipPercentage
        return this
    }

    fun getResult(): Double {
        var tipMultiplier = tipPercentage + 1
        return value * tipMultiplier
    }
}