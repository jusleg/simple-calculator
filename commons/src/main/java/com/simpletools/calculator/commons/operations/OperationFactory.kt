package com.simpletools.calculator.commons.operations

import com.simpletools.calculator.commons.operations.base.Operation
import com.simpletools.calculator.commons.helpers.* // ktlint-disable no-wildcard-imports

object OperationFactory {

    fun forId(id: String, baseValue: Double, secondValue: Double): Operation? {
        when (id) {
            PLUS -> return PlusOperation(baseValue, secondValue)
            MINUS -> return MinusOperation(baseValue, secondValue)
            DIVIDE -> return DivideOperation(baseValue, secondValue)
            MULTIPLY -> return MultiplyOperation(baseValue, secondValue)
            PERCENTAGE -> return PercentageOperation(baseValue)
            POWER -> return PowerOperation(baseValue, secondValue)
            ROOT -> return RootOperation(baseValue)
            NEGATIVE -> return NegativeOperation(baseValue)
            SQUARED -> return SquaredOperation(baseValue)
            CUBED -> return CubedOperation(baseValue)
            MODULO -> return ModuloOperation(baseValue, secondValue)
            else -> return null
        }
    }
}
