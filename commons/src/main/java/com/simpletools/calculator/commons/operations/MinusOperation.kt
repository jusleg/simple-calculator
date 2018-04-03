package com.simpletools.calculator.commons.operations

import com.simpletools.calculator.commons.operations.base.BinaryOperation

object MinusOperation : BinaryOperation() {

    override fun getResult() = secondValue - baseValue

    override fun getOperator(): String {
        return "−"
    }
}
