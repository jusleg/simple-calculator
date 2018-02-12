package com.simplemobiletools.calculator.operation

import com.simplemobiletools.calculator.helpers.Formatter
import com.simplemobiletools.calculator.operation.base.Operation
import com.simplemobiletools.calculator.operation.base.UnaryOperation

class NegativeOperation(value: Double) : UnaryOperation(value), Operation {

    override fun getResult() = value * -1

    override fun getFormula(): String = Formatter.doubleToString(value)
}