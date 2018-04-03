package com.simpletools.calculator.commons.operations

import com.simpletools.calculator.commons.operations.base.BinaryOperation

object PlusOperation : BinaryOperation() {

    override fun getResult() = baseValue + secondValue

    override fun getOperator(): String {
        return "+"
    }
}
