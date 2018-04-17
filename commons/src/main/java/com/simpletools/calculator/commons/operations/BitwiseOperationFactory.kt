package com.simpletools.calculator.commons.operations

import com.simpletools.calculator.commons.helpers.* // ktlint-disable no-wildcard-imports
import com.simpletools.calculator.commons.operations.base.* // ktlint-disable no-wildcard-imports

object BitwiseOperationFactory {

    fun forId(id: String, baseValue: Long, secondValue: Long): BitwiseOperation? {
        when (id) {
            NEGATIVE -> return bitwiseUnarySet(BitwiseNegativeOperation, baseValue)
            AND -> return bitwiseBinarySet(AndOperation, baseValue, secondValue)
            OR -> return bitwiseBinarySet(OrOperation, baseValue, secondValue)
            XOR -> return bitwiseBinarySet(XorOperation, baseValue, secondValue)
            INV -> return bitwiseUnarySet(InvOperation, baseValue)
            else -> return null
        }
    }

    private fun bitwiseBinarySet(operation: BinaryBitwiseOperation, baseValue: Long, secondValue: Long): BitwiseOperation? {
        operation.baseValue = baseValue
        operation.secondValue = secondValue
        return operation
    }

    private fun bitwiseUnarySet(operation: UnaryBitwiseOperation, value: Long): BitwiseOperation? {
        operation.value = value
        return operation
    }
}