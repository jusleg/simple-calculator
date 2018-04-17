package com.simpletools.calculator.commons.operations

import com.simpletools.calculator.commons.operations.base.BinaryBitwiseOperation

object OrOperation : BinaryBitwiseOperation() {

    override fun getResult() = secondValue.or(baseValue)

    override fun getOperator(): String {
        return "|"
    }
}