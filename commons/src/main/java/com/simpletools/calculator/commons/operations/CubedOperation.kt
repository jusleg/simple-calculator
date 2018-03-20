package com.simpletools.calculator.commons.operations

import com.simpletools.calculator.commons.helpers.Formatter
import com.simpletools.calculator.commons.operations.base.Operation
import com.simpletools.calculator.commons.operations.base.UnaryOperation

class CubedOperation(value: Double) : UnaryOperation(value), Operation {

    override fun getResult() = Math.pow(value, 3.0)

    override fun getFormula(): String {
        var formula : String = ""
        if (value < 0) {
            formula = "(" + Formatter.doubleToString(value) + ")³"
        } else {
            formula = Formatter.doubleToString(value) + "³"
        }
        return formula
    }
}