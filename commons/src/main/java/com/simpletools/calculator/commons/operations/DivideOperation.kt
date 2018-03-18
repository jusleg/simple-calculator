package com.simpletools.calculator.commons.operations

import com.simpletools.calculator.commons.helpers.Formatter
import com.simpletools.calculator.commons.operations.base.BinaryOperation
import com.simpletools.calculator.commons.operations.base.Operation

class DivideOperation(baseValue: Double, secondValue: Double) : BinaryOperation(baseValue, secondValue), Operation {

    override fun getResult(): Double {
        var result = 0.0
        if (secondValue != 0.0) {
            result = secondValue / baseValue
        }
        return result
    }

    override fun getFormula(): String {
        return Formatter.doubleToString(secondValue) + "÷" + Formatter.doubleToString(baseValue)
    }
}
