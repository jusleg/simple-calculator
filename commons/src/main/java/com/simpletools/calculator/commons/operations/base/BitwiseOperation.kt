package com.simpletools.calculator.commons.operations.base

abstract class BitwiseOperation {

    abstract fun getResult(): Long

    abstract fun getBinaryFormula(): String

    abstract fun getOctalFormula(): String

    abstract fun getDecimalFormula(): String
}