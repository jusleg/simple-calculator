package com.simpletools.calculator.commons.operations

import com.simpletools.calculator.commons.helpers.Formatter
import com.simpletools.calculator.commons.operations.base.BinaryOperation
import com.simpletools.calculator.commons.operations.base.Operation

class PowerOperation(baseValue: Double, secondValue: Double) : BinaryOperation(baseValue, secondValue), Operation {


    override fun getResult() = Math.pow(baseValue, secondValue)

    override fun getFormula(): String {
        val formula : String
        val baseNegative = baseValue < 0.0
        val secondNegative = secondValue < 0.0
        if (!baseNegative && !secondNegative) {
            formula = Formatter.doubleToString(baseValue) + "^" + Formatter.doubleToString(secondValue)
        }
        else if (baseNegative && !secondNegative) {
            formula = "(" + Formatter.doubleToString(baseValue) + ")^" + Formatter.doubleToString(secondValue)
        }
        else if (!baseNegative && secondNegative) {
            formula = Formatter.doubleToString(baseValue) + "^(" + Formatter.doubleToString(secondValue) + ")"
        }
        else {
            formula = "(" + Formatter.doubleToString(baseValue) + ")^(" + Formatter.doubleToString(secondValue) + ")"
        }
        return formula
    }
}
