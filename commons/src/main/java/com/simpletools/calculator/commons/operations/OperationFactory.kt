package com.simpletools.calculator.commons.operations

import com.simpletools.calculator.commons.operations.base.Operation
import com.simpletools.calculator.commons.helpers.PLUS
import com.simpletools.calculator.commons.helpers.MINUS
import com.simpletools.calculator.commons.helpers.DIVIDE
import com.simpletools.calculator.commons.helpers.MULTIPLY
import com.simpletools.calculator.commons.helpers.PERCENTAGE
import com.simpletools.calculator.commons.helpers.POWER
import com.simpletools.calculator.commons.helpers.ROOT
import com.simpletools.calculator.commons.helpers.NEGATIVE

object OperationFactory {

    fun forId(id: String, baseValue: Double, secondValue: Double): Operation? {
        when (id) {
            PLUS -> return PlusOperation(baseValue, secondValue)
            MINUS -> return MinusOperation(baseValue, secondValue)
            DIVIDE -> return DivideOperation(baseValue, secondValue)
            MULTIPLY -> return MultiplyOperation(baseValue, secondValue)
            PERCENTAGE -> return PercentageOperation(baseValue)
            POWER -> return PowerOperation(secondValue, baseValue)
            ROOT -> return RootOperation(baseValue)
            NEGATIVE -> return NegativeOperation(baseValue)
            SQUARED -> return SquaredOperation(baseValue)
            CUBED -> return CubedOperation(baseValue)
            else -> return null
        }
    }
}
