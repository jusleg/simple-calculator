package com.simpletools.calculator.commons.operations.base

import com.simpletools.calculator.commons.helpers.Formatter

abstract class BinaryOperation : Operation() {
    var baseValue: Double = 0.0
    var secondValue: Double = 0.0

    override fun getFormula(): String {
        val baseString = Formatter.doubleToString(baseValue, true)
        val secondString = Formatter.doubleToString(secondValue, true)
        return secondString + getOperator() + baseString
    }

    abstract fun getOperator(): String
}
