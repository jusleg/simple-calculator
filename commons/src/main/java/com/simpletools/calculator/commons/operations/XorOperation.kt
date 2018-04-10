package com.simpletools.calculator.commons.operations

import com.simpletools.calculator.commons.operations.base.BitwiseOperation

object XorOperation : BitwiseOperation() {

    override fun getResult() = secondValue.xor(baseValue)

    override fun getOperator(): String {
        return "XOR"
    }
}