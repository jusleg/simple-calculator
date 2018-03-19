package com.simpletools.calculator.commons.operations

import com.simpletools.calculator.commons.operations.base.UnaryOperation

object SquaredOperation : UnaryOperation() {

    override fun getResult() = Math.pow(value, 2.0)

    override fun applyOperator(str: String): String {
        return "$str²"
    }
}