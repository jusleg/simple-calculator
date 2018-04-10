package com.simpletools.calculator.commons.operations.base

abstract class BitwiseOperation {
    var baseValue: Int = 0
    var secondValue: Int = 0

    abstract fun getResult(): Int

    fun getFormula(): String {
        val baseString = Integer.toString(baseValue)
        val secondString = Integer.toString(secondValue)
        return secondString + getOperator() + baseString
    }

    abstract fun getOperator(): String
}