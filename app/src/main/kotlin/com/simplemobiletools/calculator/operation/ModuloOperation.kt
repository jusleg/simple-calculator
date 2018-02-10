package com.simplemobiletools.calculator.operation

import com.simplemobiletools.calculator.helpers.Formatter
import com.simplemobiletools.calculator.operation.base.BinaryOperation
import com.simplemobiletools.calculator.operation.base.Operation

class ModuloOperation(baseValue: Double, secondValue: Double) : BinaryOperation(baseValue, secondValue), Operation {

    override fun getResult(): Double {
        var result = 0.0
        if (secondValue != 0.0) {
            result = secondValue % baseValue
        }
        return result
    }

    override fun getFormula(): String {
        return Formatter.doubleToString(secondValue) + "%" + Formatter.doubleToString(baseValue)
    }
}
