package com.simpletools.calculator.commons.operations

import com.simpletools.calculator.commons.operations.base.BinaryOperation

class PowerOperation(baseValue: Double, secondValue: Double) : BinaryOperation(baseValue, secondValue) {

    override fun getResult() = Math.pow(secondValue, baseValue)

    override fun getOperator(): String {
        return "^"
    }
}
