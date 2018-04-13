package com.simpletools.calculator.commons.operations.base

abstract class UnaryBitwiseOperation : BitwiseOperation() {
    var value: Int = 0

    override fun getBinaryFormula(): String {
        var baseString: String = ""
        if (value < 0) {
            baseString = "-" + Integer.toBinaryString(value * -1)// toBinaryString with negative integer give errors
        } else {
            baseString = Integer.toBinaryString(value)
        }
        return applyOperator(baseString)
    }

    override fun getOctalFormula(): String {
        var baseString: String = ""
        if (value < 0) {
            baseString = "-" + Integer.toOctalString(value * -1) // toOctalString with negative integer give errors
        } else {
            baseString = Integer.toOctalString(value)
        }
        return applyOperator(baseString)
    }

    override fun getDecimalFormula(): String {
        val baseString = Integer.toString(value)
        return applyOperator(baseString)
    }

    protected abstract fun applyOperator(str: String): String
}