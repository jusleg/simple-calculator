package com.simpletools.calculator.commons.operations.base

abstract class Operation {

    abstract fun getResult(): Double

    abstract fun getFormula(): String

    protected fun parenthesize(str: String): String {
        return "($str)"
    }
}
