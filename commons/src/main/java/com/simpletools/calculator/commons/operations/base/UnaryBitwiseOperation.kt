package com.simpletools.calculator.commons.operations.base

abstract class UnaryBitwiseOperation : BitwiseOperation() {
    var value: Int = 0

    override fun getFormula(): String {
        val baseString = Integer.toString(value)
        return applyOperator(baseString)
    }

    protected abstract fun applyOperator(str: String): String
}