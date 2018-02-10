package com.simplemobiletools.calculator.operation

import com.simplemobiletools.calculator.helpers.Formatter
import com.simplemobiletools.calculator.operation.base.BinaryOperation
import com.simplemobiletools.calculator.operation.base.Operation

class MultiplyOperation(baseValue: Double, secondValue: Double) : BinaryOperation(baseValue, secondValue), Operation {

    override fun getResult() = baseValue * secondValue

    override fun getFormula(): String {
        return Formatter.doubleToString(secondValue) + "×" + Formatter.doubleToString(baseValue)
    }
}
