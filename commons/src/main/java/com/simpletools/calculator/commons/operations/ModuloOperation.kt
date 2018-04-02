package com.simpletools.calculator.commons.operations

import com.simpletools.calculator.commons.operations.base.BinaryOperation
import java.lang.Math.abs

class ModuloOperation(baseValue: Double, secondValue: Double) : BinaryOperation(baseValue, secondValue) {

    override fun getResult(): Double {

        if (baseValue == 0.0) return secondValue

        val remainder = secondValue % baseValue
        if (remainder < 0) {
            return remainder + abs(baseValue)
        }
        return remainder
    }

    override fun getOperator(): String {
        return "mod"
    }
}
