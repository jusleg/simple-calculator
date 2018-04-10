package com.simpletools.calculator.commons.operations.base

abstract class UnaryBitwiseOperation {
    var value: Int = 0

    abstract fun getResult(): Int

    fun getFormula(): String {
        val baseString = Integer.toString(value)
        return applyOperator(baseString)
    }

    protected abstract fun applyOperator(str: String): String
}