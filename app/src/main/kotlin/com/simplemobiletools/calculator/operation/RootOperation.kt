package com.simplemobiletools.calculator.operation

import com.simplemobiletools.calculator.helpers.Formatter
import com.simplemobiletools.calculator.operation.base.Operation
import com.simplemobiletools.calculator.operation.base.UnaryOperation

class RootOperation(value: Double) : UnaryOperation(value), Operation {

    override fun getResult() = Math.sqrt(value)

    override fun getFormula(): String {
        return "√" + Formatter.doubleToString(value)
    }
}
