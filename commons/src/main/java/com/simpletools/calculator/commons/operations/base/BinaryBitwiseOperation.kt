package com.simpletools.calculator.commons.operations.base

abstract class BinaryBitwiseOperation : BitwiseOperation() {
    var baseValue: Long = 0
    var secondValue: Long = 0

    override fun getBinaryFormula(): String {
        var baseString: String = ""
        var secondString: String = ""
        if (baseValue < 0) {
            baseValue *= -1
            baseString = "-" + baseValue.toString(2) // toBinaryString with negative Long give errors
        } else {
            baseString = baseValue.toString(2)
        }
        if (secondValue < 0) {
            secondValue *= -1
            secondString = "-" + secondValue.toString(2) // toBinaryString with negative Long give errors
        } else {
            secondString = secondValue.toString(2)
        }
        return secondString + getOperator() + baseString
    }

    override fun getOctalFormula(): String {
        var baseString: String = ""
        var secondString: String = ""
        if (baseValue < 0) {
            baseValue *= -1
            baseString = "-" + baseValue.toString(8) // toOctalString with negative Long give errors
        } else {
            baseString = baseValue.toString(8)
        }
        if (secondValue < 0) {
            secondValue *= -1
            secondString = "-" + secondValue.toString(8) // toOctalString with negative Long give errors
        } else {
            secondString = secondValue.toString(8)
        }
        return secondString + getOperator() + baseString
    }

    override fun getDecimalFormula(): String {
        val baseString = baseValue.toString()
        val secondString = secondValue.toString()
        return secondString + getOperator() + baseString
    }

    abstract fun getOperator(): String
}