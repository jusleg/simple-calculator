package com.simpletools.calculator.commons.operations

import com.simpletools.calculator.commons.operations.base.UnaryOperation

class PercentageOperation(baseValue: Double) : UnaryOperation(baseValue) {

    override fun getResult() = value * 0.01

    override fun applyOperator(str: String): String {
        return "$str%"
    }
}