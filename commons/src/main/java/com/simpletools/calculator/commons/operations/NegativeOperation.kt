package com.simpletools.calculator.commons.operations

import com.simpletools.calculator.commons.helpers.Formatter
import com.simpletools.calculator.commons.operations.base.Operation
import com.simpletools.calculator.commons.operations.base.UnaryOperation

class NegativeOperation(value: Double) : UnaryOperation(value), Operation {

    override fun getResult() = value * -1

    override fun getFormula(): String = Formatter.doubleToString(value)
}