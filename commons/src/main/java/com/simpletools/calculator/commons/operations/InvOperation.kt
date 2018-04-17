package com.simpletools.calculator.commons.operations

import com.simpletools.calculator.commons.operations.base.UnaryBitwiseOperation

object InvOperation : UnaryBitwiseOperation() {

    override fun getResult() = value.inv()

    override fun applyOperator(str: String): String {
        return "~($str)"
    }
}