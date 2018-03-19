package com.simpletools.calculator.commons.operations

import com.simpletools.calculator.commons.operations.base.BinaryOperation

object PowerOperation : BinaryOperation() {

    override fun getResult() = Math.pow(secondValue, baseValue)

    override fun getOperator(): String {
        return "^"
    }
}
