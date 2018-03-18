package com.simpletools.calculator.commons.operations

import com.simpletools.calculator.commons.helpers.Formatter
import com.simpletools.calculator.commons.operations.base.BinaryOperation
import com.simpletools.calculator.commons.operations.base.Operation

class PlusOperation(baseValue: Double, secondValue: Double) : BinaryOperation(baseValue, secondValue), Operation {

    override fun getResult() = baseValue + secondValue

    override fun getFormula(): String {
        return Formatter.doubleToString(secondValue) + "+" + Formatter.doubleToString(baseValue)
    }
}
