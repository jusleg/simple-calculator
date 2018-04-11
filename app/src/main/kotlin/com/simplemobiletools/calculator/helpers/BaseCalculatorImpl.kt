package com.simplemobiletools.calculator.helpers

import android.content.Context
import com.simpletools.calculator.commons.R
import com.simpletools.calculator.commons.helpers.Calculator
import com.simpletools.calculator.commons.operations.BitwiseNegativeOperation
import com.simpletools.calculator.commons.operations.BitwiseOperationFactory
import com.simpletools.calculator.commons.operations.base.UnaryOperation
import com.simpletools.calculator.commons.operations.base.UnaryBitwiseOperation
import com.simpletools.calculator.commons.operations.base.BinaryBitwiseOperation
import com.simpletools.calculator.commons.operations.base.BitwiseOperation
import com.simpletools.calculator.commons.helpers.* // ktlint-disable no-wildcard-imports

class BaseCalculatorImpl(calculator: Calculator, val context: Context) {
    private var mCallback: Calculator? = calculator

    private var firstNumber: Int = 0
    private var secondNumber: Int = 0
    private var operator: String? = ""
    private var lastOperator: String? = ""
    private var lastOperand: Int = 0
    private var secondNumberSet: Boolean = false
    private var digits = 0
    private var lastIsOperation: Boolean = false
    private var firstNumberSign: Int = 1

    init {
        resetValues()
        setValue("0")
        setFormula("")
    }

    fun numpadClicked(id: Int) {
        when (id) {
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

    fun handleOperation(operation: String) {
        if (BitwiseOperationFactory.forId(operation, firstNumber, secondNumber) is BitwiseNegativeOperation) {
            return negateNumber()
        }
        // Handle chained operations
        if (secondNumberSet) {
            handleEquals()
        }

        operator = operation
        if (BitwiseOperationFactory.forId(operator!!, firstNumber, secondNumber) is UnaryBitwiseOperation) {
            if (lastIsOperation == true) swapRegisters()
            handleEquals()
        } else if (BitwiseOperationFactory.forId(operator!!, firstNumber, secondNumber) is BinaryBitwiseOperation && lastIsOperation == false) {
            lastOperator = operation
            swapRegisters()
        }
    }

    fun handleEquals() {
        val operation: BitwiseOperation?
        if (operator != "") { // Handle new operation
            operation = BitwiseOperationFactory.forId(operator!!, firstNumberWithSign(), secondNumber)

            if ((operation != null) && (digits > 0 || operation is UnaryOperation)) {
                if (operation !is UnaryOperation) {
                    lastOperand = firstNumberWithSign()
                }
                executeCalculation(operation)
            }
        } else { // Handle chained equals
            operation = BitwiseOperationFactory.forId(lastOperator!!, lastOperand, firstNumberWithSign())

            if (operation != null) {
                executeCalculation(operation)
            }
        }
    }

    fun handleReset() {
        setAllClear()
        resetValues()
        lastOperator = ""
        lastOperand = 0
    }

    fun handleClear() {
        if (!secondNumberSet) {
            handleReset()
            setAllClear()
        } else {
            firstNumber = 0
            firstNumberSign = 1
            setAllClear()
            setValue("0")
            secondNumberSet = false
        }
    }

    private fun executeCalculation(operation: BitwiseOperation) {
        setAllClear()
        resetValues()
        firstNumber = operation.getResult()
        setValue(Integer.toString(firstNumberWithSign()))
        setFormula(operation.getFormula())
        lastIsOperation = false
    }

    fun convertToDecimal(baseFrom: String) {
        if (secondNumberSet || lastIsOperation) {
            setAllClear()
            resetValues()
        } else if (baseFrom.equals(OCT)) {
            firstNumber = convertDec(OCT)
            setValue(firstNumber.toString())
            lastIsOperation = false
        } else {
            firstNumber = convertDec(BIN)
            setValue(firstNumber.toString())
            lastIsOperation = false
        }
    }

    fun convertToOctal(baseFrom: String) {
        if (secondNumberSet || lastIsOperation) {
            setAllClear()
            resetValues()
        } else if (baseFrom.equals(DEC)) {
            firstNumber = convertOct(DEC)
            setValue(firstNumber.toString())
            lastIsOperation = false
        } else {
            firstNumber = convertOct(BIN)
            setValue(firstNumber.toString())
            lastIsOperation = false
        }
    }

    fun convertToBinary(baseFrom: String) {
        if (secondNumberSet || lastIsOperation) {
            setAllClear()
            resetValues()
        } else if (baseFrom.equals(DEC)) {
            firstNumber = convertBin(DEC)
            setValue(firstNumber.toString())
            lastIsOperation = false
        } else {
            firstNumber = convertBin(OCT)
            setValue(firstNumber.toString())
            lastIsOperation = false
        }
    }

    private fun convertDec(baseFrom: String): Int {
        if (baseFrom.equals(OCT)) {
            return Integer.valueOf(firstNumberWithSign().toString(), 8)
        } else if (baseFrom.equals(BIN)) {
            return Integer.valueOf(firstNumberWithSign().toString(), 2)
        } else return 0
    }

    private fun convertOct(baseFrom: String): Int {
        if (baseFrom.equals(DEC)) {
            return Integer.valueOf(Integer.toOctalString(firstNumberWithSign()))
        } else if (baseFrom.equals(BIN)) {
            var decimalValue = convertDec(BIN)
            return Integer.valueOf(Integer.toOctalString(decimalValue))
        } else return 0
    }

    private fun convertBin(baseFrom: String): Int {
        if (baseFrom.equals(DEC)) {
            return Integer.valueOf(Integer.toBinaryString(firstNumberWithSign()))
        } else if (baseFrom.equals(OCT)) {
            var decimalValue = convertDec(OCT)
            return Integer.valueOf(Integer.toBinaryString(decimalValue))
        } else return 0
    }

    private fun setValue(value: String) {
        mCallback!!.setValue(value)
    }

    private fun setFormula(value: String) {
        mCallback!!.setFormula(value)
    }

    private fun resetValues() {
        firstNumber = 0
        secondNumber = 0
        operator = ""
        secondNumberSet = false
        setValue("0")
        setFormula("")
        digits = 0
    }

    fun addDigit(i: Int) {
        if (operator != "") {
            secondNumberSet = true
            setClear()
        } else if (digits == 0 && firstNumberSign == 1) {
            resetValues()
        }

        firstNumber = firstNumber * 10 + i
        setValue(Integer.toString(firstNumberWithSign()))

        digits++
    }

    private fun swapRegisters() {
        firstNumber = firstNumberWithSign()
        firstNumber += secondNumber
        secondNumber = firstNumber - secondNumber
        firstNumber -= secondNumber
        digits = 0
        lastIsOperation = true
        firstNumberSign = 1
    }

    private fun setClear() {
        mCallback!!.setClear("CE")
    }

    private fun setAllClear() {
        mCallback!!.setClear("AC")
    }

    private fun firstNumberWithSign(): Int {
        return firstNumber * firstNumberSign
    }

    private fun negateNumber() {
        firstNumberSign *= -1
        setValue(Integer.toString(firstNumberWithSign()))
    }
}