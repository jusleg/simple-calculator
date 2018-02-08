package com.simplemobiletools.calculator.helpers

import android.content.Context
import com.simplemobiletools.calculator.R
import com.simplemobiletools.calculator.operation.OperationFactory
import com.simplemobiletools.calculator.operation.base.UnaryOperation

class CalculatorImpl(calculator: Calculator, val context: Context) {
    var lastKey: String? = null
    private var mCallback: Calculator? = calculator

    private var firstNumber: Double = 0.0
    private var secondNumber: Double = 0.0
    private var operator: String? = ""
    private var decimalClicked: Boolean = false
    private var decimalCounter = 0
    private var secondNumberSet: Boolean = false
    private var digits = 0

    init {
        resetValues()
        setValue("0")
        setFormula("")
    }

    private fun resetValues() {
        firstNumber = 0.0
        secondNumber = 0.0
        decimalCounter = 0
        decimalClicked = false
        operator = ""
        secondNumberSet = false
        lastKey = ""
        setValue("0")
        digits = 0
    }

    fun setValue(value: String) {
        mCallback!!.setValue(value, context)
    }

    private fun setFormula(value: String) {
        mCallback!!.setFormula(value, context)
    }

    fun numpadClicked(id: Int) {
        if (id != R.id.btn_decimal){
            if (decimalClicked) decimalCounter--
            if (operator != "") {
                secondNumberSet = true
            } else if (digits == 0) {
                resetValues()
            }
            digits++
        }

        when (id) {
            R.id.btn_decimal -> decimalClicked()
            R.id.btn_0 -> addDigit(0)
            R.id.btn_1 -> addDigit(1)
            R.id.btn_2 -> addDigit(2)
            R.id.btn_3 -> addDigit(3)
            R.id.btn_4 -> addDigit(4)
            R.id.btn_5 -> addDigit(5)
            R.id.btn_6 -> addDigit(6)
            R.id.btn_7 -> addDigit(7)
            R.id.btn_8 -> addDigit(8)
            R.id.btn_9 -> addDigit(9)
        }
    }

    private fun decimalClicked() {
        decimalClicked = true
    }

    private fun addDigit(i: Int) {
        firstNumber = if (!decimalClicked) signumMultiply(firstNumber) * (Math.abs(firstNumber)  * 10 + i)
            else signumMultiply(firstNumber) * (Math.abs(firstNumber) + i * Math.pow(10.0, decimalCounter.toDouble()))
        setValue(Formatter.doubleToString(firstNumber))
    }

    fun handleOperation(operation: String) {
        handleEquals()
        operator = operation
        if (OperationFactory.forId(operator!!, firstNumber, secondNumber) is UnaryOperation) {
            handleEquals()
        } else {
            firstNumber = firstNumber + secondNumber
            secondNumber = firstNumber - secondNumber
            firstNumber = firstNumber - secondNumber
            decimalClicked = false
            decimalCounter = 0
            digits = 0
        }
    }

    fun handleClear() {
        handleReset()
    }

    fun handleEquals() {
        val operation = OperationFactory.forId(operator!!, firstNumber, secondNumber)

        if (operation != null && (digits > 0 || operation is UnaryOperation)) {
            var result = operation.getResult()
            resetValues()
            firstNumber = result
            setValue(Formatter.doubleToString(result))
        }
    }

    fun handleReset() {
        resetValues() //To change body of created functions use File | Settings | File Templates.
    }

    private fun signumMultiply(i: Double): Double {
        return if (Math.signum(i) == -1.0) -1.0 else 1.0 // slight modification of signum to use in multiplication
    }
}
