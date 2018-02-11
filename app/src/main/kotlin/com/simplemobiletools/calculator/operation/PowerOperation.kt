package com.simplemobiletools.calculator.operation

import com.simplemobiletools.calculator.helpers.Formatter
import com.simplemobiletools.calculator.operation.base.BinaryOperation
import com.simplemobiletools.calculator.operation.base.Operation

class PowerOperation(baseValue: Double, secondValue: Double) : BinaryOperation(baseValue, secondValue), Operation {

    override fun getResult(): Double {
        var result = Math.pow(secondValue,baseValue)
        if (java.lang.Double.isInfinite(result) || java.lang.Double.isNaN(result))
            result = 0.0
        return result
    }

    override fun getFormula(): String {
        return Formatter.doubleToString(secondValue) + "^" + Formatter.doubleToString(baseValue)
    }
}
