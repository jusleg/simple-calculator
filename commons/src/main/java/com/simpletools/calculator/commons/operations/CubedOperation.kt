package com.simpletools.calculator.commons.operations

import com.simpletools.calculator.commons.operations.base.UnaryOperation

class CubedOperation(value: Double) : UnaryOperation(value) {

    override fun getResult() = Math.pow(value, 3.0)

    override fun applyOperator(str: String): String {
        return "$str³"
    }
}