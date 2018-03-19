package com.simpletools.calculator.commons.operations

import com.simpletools.calculator.commons.operations.base.BinaryOperation

object MultiplyOperation : BinaryOperation() {

    override fun getResult() = baseValue * secondValue

    override fun getOperator(): String {
        return "×"
    }
}
