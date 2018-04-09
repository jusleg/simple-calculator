package com.simpletools.calculator.commons.operations.base

import com.simpletools.calculator.commons.helpers.Formatter

abstract class UnaryOperation : Operation() {
    var value: Double = 0.0

    override fun getFormula(): String {
        val baseString = Formatter.doubleToString(value, true)
        return applyOperator(baseString)
    }

    protected abstract fun applyOperator(str: String): String
}
