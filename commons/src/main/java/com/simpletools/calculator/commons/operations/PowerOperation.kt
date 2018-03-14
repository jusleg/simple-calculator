package com.simpletools.calculator.commons.operations

import com.simpletools.calculator.commons.helpers.Formatter
import com.simpletools.calculator.commons.operations.base.BinaryOperation
import com.simpletools.calculator.commons.operations.base.Operation

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
