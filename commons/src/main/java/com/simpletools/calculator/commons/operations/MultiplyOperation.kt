package com.simpletools.calculator.commons.operations

import com.simpletools.calculator.commons.operations.base.BinaryOperation

class MultiplyOperation(baseValue: Double, secondValue: Double) : BinaryOperation(baseValue, secondValue) {

    override fun getResult() = baseValue * secondValue

    override fun getOperator(): String {
        return "×"
    }
}
