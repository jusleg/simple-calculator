package com.simpletools.calculator.commons.operations

import com.simpletools.calculator.commons.operations.base.BitwiseOperation

object OrOperation : BitwiseOperation() {

    override fun getResult() = secondValue.or(baseValue)

    override fun getOperator(): String {
        return "OR"
    }
}