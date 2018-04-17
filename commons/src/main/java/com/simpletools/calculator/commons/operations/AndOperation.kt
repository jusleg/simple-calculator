package com.simpletools.calculator.commons.operations

import com.simpletools.calculator.commons.operations.base.BinaryBitwiseOperation

object AndOperation : BinaryBitwiseOperation() {

    override fun getResult() = secondValue.and(baseValue)

    override fun getOperator(): String {
        return "&"
    }
}