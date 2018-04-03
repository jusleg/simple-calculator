package com.simpletools.calculator.commons.operations

import com.simpletools.calculator.commons.operations.base.BinaryOperation

class DivideOperation(baseValue: Double, secondValue: Double) : BinaryOperation(baseValue, secondValue) {

    override fun getResult() = secondValue / baseValue

    override fun getOperator(): String {
        return "÷"
    }
}
