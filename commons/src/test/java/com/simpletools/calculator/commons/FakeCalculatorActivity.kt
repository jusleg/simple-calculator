package com.simpletools.calculator.commons

import com.simpletools.calculator.commons.activities.SimpleActivity
import com.simpletools.calculator.commons.helpers.Calculator

class FakeCalculator : SimpleActivity(), Calculator {

    private var formula: String = ""
    private var result: String = ""

    override fun setFormula(value: String) {
        formula = value
    }

    override fun setValue(value: String) {
        result = value
    }

    override fun getResult(): String {
        return result
    }

    override fun getFormula(): String {
        return formula
    }

    override fun setClear(value: String) {}
    override fun displayToast(message: String) {}
}