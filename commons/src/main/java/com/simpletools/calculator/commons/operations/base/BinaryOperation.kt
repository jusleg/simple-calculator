package com.simpletools.calculator.commons.operations.base

import com.simpletools.calculator.commons.helpers.Formatter

abstract class BinaryOperation : Operation() {
    var baseValue: Double = 0.0
    var secondValue: Double = 0.0

    override fun getFormula(): String {
        val baseString = if (baseValue < 0.0) parenthesize(Formatter.doubleToString(baseValue)) else Formatter.doubleToString(baseValue)
        val secondString = if (secondValue < 0.0) parenthesize(Formatter.doubleToString(secondValue)) else Formatter.doubleToString(secondValue)
        return secondString + getOperator() + baseString
    }

    abstract fun getOperator(): String
}
