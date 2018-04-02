package com.simpletools.calculator.commons.operations

import com.simpletools.calculator.commons.operations.base.UnaryOperation

class RootOperation(value: Double) : UnaryOperation(value) {

    override fun getResult() = Math.sqrt(value)

    override fun applyOperator(str: String): String {
        return "√$str"
    }
}
