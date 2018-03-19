package com.simpletools.calculator.commons.operations

import com.simpletools.calculator.commons.operations.base.UnaryOperation

object RootOperation : UnaryOperation() {

    override fun getResult() = Math.sqrt(value)

    override fun applyOperator(str: String): String {
        return "√$str"
    }
}
