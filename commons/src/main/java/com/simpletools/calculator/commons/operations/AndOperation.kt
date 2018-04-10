package com.simpletools.calculator.commons.operations

import com.simpletools.calculator.commons.operations.base.BitwiseOperation

object AndOperation : BitwiseOperation() {

    override fun getResult() = secondValue.and(baseValue)

    override fun getOperator(): String {
        return "AND"
    }
}