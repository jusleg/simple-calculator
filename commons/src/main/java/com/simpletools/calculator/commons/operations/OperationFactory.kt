package com.simpletools.calculator.commons.operations

import com.simpletools.calculator.commons.operations.base.Operation
import com.simpletools.calculator.commons.helpers.* // ktlint-disable no-wildcard-imports
import com.simpletools.calculator.commons.operations.base.BinaryOperation
import com.simpletools.calculator.commons.operations.base.UnaryOperation

object OperationFactory {

    fun forId(id: String, baseValue: Double, secondValue: Double): Operation? {
        when (id) {
            PLUS -> return binarySet(PlusOperation, baseValue, secondValue)
            MINUS -> return binarySet(MinusOperation, baseValue, secondValue)
            DIVIDE -> return binarySet(DivideOperation, baseValue, secondValue)
            MULTIPLY -> return binarySet(MultiplyOperation, baseValue, secondValue)
            PERCENTAGE -> return unarySet(PercentageOperation, baseValue)
            POWER -> return binarySet(PowerOperation, baseValue, secondValue)
            ROOT -> return unarySet(RootOperation, baseValue)
            NEGATIVE -> return unarySet(NegativeOperation, baseValue)
            SQUARED -> return unarySet(SquaredOperation, baseValue)
            CUBED -> return unarySet(CubedOperation, baseValue)
            MODULO -> return binarySet(ModuloOperation, baseValue, secondValue)
            else -> return null
        }
    }

    private fun binarySet(operation: BinaryOperation, baseValue: Double, secondValue: Double): Operation? {
        operation.baseValue = baseValue
        operation.secondValue = secondValue
        return operation
    }

    private fun unarySet(operation: UnaryOperation, value: Double): Operation? {
        operation.value = value
        return operation
    }
}
