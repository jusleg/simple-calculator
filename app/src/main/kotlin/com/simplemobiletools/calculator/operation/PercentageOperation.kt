package com.simplemobiletools.calculator.operation

import com.simplemobiletools.calculator.operation.base.Operation
import com.simplemobiletools.calculator.operation.base.UnaryOperation

class PercentageOperation(baseValue: Double) : UnaryOperation(baseValue), Operation {

    override fun getResult(): Double {
        var result = value * 0.01
        return result
    }

}