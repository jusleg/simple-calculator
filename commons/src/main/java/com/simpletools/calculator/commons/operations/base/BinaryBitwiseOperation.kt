package com.simpletools.calculator.commons.operations.base

abstract class BinaryBitwiseOperation : BitwiseOperation() {
    var baseValue: Int = 0
    var secondValue: Int = 0

    override fun getBinaryFormula(): String {
        var baseString: String = ""
        var secondString: String = ""
        if (baseValue < 0) {
            baseString = "-" + Integer.toBinaryString(baseValue * -1) // toBinaryString with negative integer give errors
        } else {
            baseString = Integer.toBinaryString(baseValue)
        }
        if (secondValue < 0) {
            secondString = "-" + Integer.toBinaryString(secondValue * -1) // toBinaryString with negative integer give errors
        } else {
            secondString = Integer.toBinaryString(secondValue)
        }
        return secondString + getOperator() + baseString
    }

    override fun getOctalFormula(): String {
        var baseString: String = ""
        var secondString: String = ""
        if (baseValue < 0) {
            baseString = "-" + Integer.toOctalString(baseValue * -1) // toOctalString with negative integer give errors
        } else {
            baseString = Integer.toOctalString(baseValue)
        }
        if (secondValue < 0) {
            secondString = "-" + Integer.toOctalString(secondValue * -1) // toOctalString with negative integer give errors
        } else {
            secondString = Integer.toOctalString(secondValue)
        }
        return secondString + getOperator() + baseString
    }

    override fun getDecimalFormula(): String {
        val baseString = Integer.toString(baseValue)
        val secondString = Integer.toString(secondValue)
        return secondString + getOperator() + baseString
    }

    abstract fun getOperator(): String
}