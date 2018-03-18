package com.simpletools.calculator.commons.operations

import com.simpletools.calculator.commons.helpers.Formatter
import com.simpletools.calculator.commons.operations.base.Operation
import com.simpletools.calculator.commons.operations.base.UnaryOperation

class RootOperation(value: Double) : UnaryOperation(value), Operation {

    override fun getResult() = Math.sqrt(value)

    override fun getFormula(): String {
        return "√" + Formatter.doubleToString(value)
    }
}
