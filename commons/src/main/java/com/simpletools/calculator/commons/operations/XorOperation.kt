package com.simpletools.calculator.commons.operations

import com.simpletools.calculator.commons.operations.base.BinaryBitwiseOperation

object XorOperation : BinaryBitwiseOperation() {

    override fun getResult() = secondValue.xor(baseValue)

    override fun getOperator(): String {
        return "^"
    }
}