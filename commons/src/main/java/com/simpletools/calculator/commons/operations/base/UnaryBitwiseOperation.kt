package com.simpletools.calculator.commons.operations.base

abstract class UnaryBitwiseOperation : BitwiseOperation() {
    var value: Long = 0

    override fun getBinaryFormula(): String {
        var baseString: String = ""
        if (value < 0) {
            value *= -1
            baseString = "-" + value.toString(2)// toBinaryString with negative integer give errors
        } else {
            baseString = value.toString(2)
        }
        return applyOperator(baseString)
    }

    override fun getOctalFormula(): String {
        var baseString: String = ""
        if (value < 0) {
            value *= -1
            baseString = "-" + value.toString(8) // toOctalString with negative integer give errors
        } else {
            baseString = value.toString(8)
        }
        return applyOperator(baseString)
    }

    override fun getDecimalFormula(): String {
        val baseString = value.toString()
        return applyOperator(baseString)
    }

    protected abstract fun applyOperator(str: String): String
}