package com.simpletools.calculator.commons.operations.base

import com.simpletools.calculator.commons.helpers.Formatter

abstract class UnaryOperation : Operation() {
    var value : Double = 0.0

    override fun getFormula(): String {
        val baseString = if (value < 0.0) parenthesize(Formatter.doubleToString(value)) else Formatter.doubleToString(value)
        return applyOperator(baseString)
    }

    abstract fun applyOperator(str: String): String
}
