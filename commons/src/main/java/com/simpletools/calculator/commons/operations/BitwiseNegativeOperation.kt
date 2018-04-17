package com.simpletools.calculator.commons.operations

import com.simpletools.calculator.commons.operations.base.UnaryBitwiseOperation

object BitwiseNegativeOperation : UnaryBitwiseOperation() {

    override fun getResult() = value * -1

    override fun applyOperator(str: String): String {
        return ""
    }
}