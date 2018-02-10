package com.simplemobiletools.calculator.operation

import com.simplemobiletools.calculator.helpers.Formatter
import com.simplemobiletools.calculator.operation.base.BinaryOperation
import com.simplemobiletools.calculator.operation.base.Operation

class MinusOperation(baseValue: Double, secondValue: Double) : BinaryOperation(baseValue, secondValue), Operation {

    override fun getResult() = secondValue - baseValue
    override fun getFormula(): String {
        return Formatter.doubleToString(secondValue) + "-" + Formatter.doubleToString(baseValue)
    }
}
