package com.simpletools.calculator.commons.operations

import com.simpletools.calculator.commons.helpers.Formatter
import com.simpletools.calculator.commons.operations.base.Operation
import com.simpletools.calculator.commons.operations.base.UnaryOperation

class PercentageOperation(baseValue: Double) : UnaryOperation(baseValue), Operation {

    override fun getResult(): Double {
        var result = value * 0.01
        return result
    }

    override fun getFormula(): String {
        return Formatter.doubleToString(value) + "%"
    }
}