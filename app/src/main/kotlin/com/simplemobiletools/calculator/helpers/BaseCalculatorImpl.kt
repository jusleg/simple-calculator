package com.simplemobiletools.calculator.helpers

import android.content.Context
import com.simpletools.calculator.commons.R
import com.simpletools.calculator.commons.helpers.Calculator
import com.simpletools.calculator.commons.operations.BitwiseNegativeOperation
import com.simpletools.calculator.commons.operations.BitwiseOperationFactory
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
    private var currentBase: String? = DEC

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
        if (currentBase.equals(OCT)) {
            firstNumber = convertDec(OCT, firstNumber)
            secondNumber = convertDec(OCT, secondNumber)
        } else if (currentBase.equals(BIN)) {
            firstNumber = convertDec(BIN, firstNumber)
            secondNumber = convertDec(BIN, secondNumber)
        }
        val operation: BitwiseOperation?
        if (operator != "") { // Handle new operation
            operation = BitwiseOperationFactory.forId(operator!!, firstNumber, secondNumber)

            if ((operation != null) && (digits > 0 || operation is UnaryBitwiseOperation)) {
                if (operation !is UnaryBitwiseOperation) {
                    lastOperand = firstNumber
                }
                executeCalculation(operation)
            }
        } else { // Handle chained equals
            operation = BitwiseOperationFactory.forId(lastOperator!!, lastOperand, firstNumber)

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
            setAllClear()
            setValue("0")
            secondNumberSet = false
        }
    }

    private fun executeCalculation(operation: BitwiseOperation) {
        setAllClear()
        resetValues()
        lastIsOperation = false
        if (currentBase.equals(BIN)) {
            firstNumber = convertBin(DEC, operation.getResult())
            setValue(Integer.toString(firstNumber))
            setFormula(operation.getBinaryFormula())
        } else if (currentBase.equals(OCT)) {
            firstNumber = convertOct(DEC, operation.getResult())
            setValue(Integer.toString(firstNumber))
            setFormula(operation.getOctalFormula())
        } else {
            firstNumber = operation.getResult()
            setValue(Integer.toString(firstNumber))
            setFormula(operation.getDecimalFormula())
        }
    }

    fun convertToDecimal(baseFrom: String) {
        currentBase = DEC
        if (secondNumberSet || lastIsOperation) {
            handleReset()
        } else if (baseFrom.equals(OCT)) {
            var value = firstNumber
            firstNumber = convertDec(OCT, value)
            setValue(firstNumber.toString())
            lastIsOperation = false
        } else {
            var value = firstNumber
            firstNumber = convertDec(BIN, value)
            setValue(firstNumber.toString())
            lastIsOperation = false
        }
    }

    fun convertToOctal(baseFrom: String) {
        currentBase = OCT
        if (secondNumberSet || lastIsOperation) {
            handleReset()
        } else if (baseFrom.equals(DEC)) {
            var value = firstNumber
            firstNumber = convertOct(DEC, value)
            setValue(firstNumber.toString())
            lastIsOperation = false
        } else {
            var value = firstNumber
            firstNumber = convertOct(BIN, value)
            setValue(firstNumber.toString())
            lastIsOperation = false
        }
    }

    fun convertToBinary(baseFrom: String) {
        currentBase = BIN
        if (secondNumberSet || lastIsOperation) {
            handleReset()
        } else if (baseFrom.equals(DEC)) {
            var value = firstNumber
            firstNumber = convertBin(DEC, value)
            setValue(firstNumber.toString())
            lastIsOperation = false
        } else {
            var value = firstNumber
            firstNumber = convertBin(OCT, value)
            setValue(firstNumber.toString())
            lastIsOperation = false
        }
    }

    private fun convertDec(baseFrom: String, value: Int): Int {
        if (baseFrom.equals(OCT)) {
            if (value < 0) {
                return -1 * Integer.valueOf((value*-1).toString(), 8)
            } else {
                return Integer.valueOf(value.toString(), 8)
            }
        } else if (baseFrom.equals(BIN)) {
            if (value < 0) {
                return -1 * Integer.valueOf((value*-1).toString(), 2)
            } else {
                return Integer.valueOf(value.toString(), 2)
            }
        } else return 0
    }

    private fun convertOct(baseFrom: String, value: Int): Int {
        if (baseFrom.equals(DEC)) {
            if (value < 0) {
                return -1 * Integer.valueOf(Integer.toOctalString(value * -1)) // toOctalString with negative integer give errors
            } else {
                return Integer.valueOf(Integer.toOctalString(value))
            }
        } else if (baseFrom.equals(BIN)) {
            var decimalValue = convertDec(BIN, value)
            if (value < 0) {
                return -1 * Integer.valueOf(Integer.toOctalString(decimalValue * -1)) // toOctalString with negative integer give errors
            } else {
                return Integer.valueOf(Integer.toOctalString(decimalValue))
            }
        } else return 0
    }

    private fun convertBin(baseFrom: String, value: Int): Int {
        if (baseFrom.equals(DEC)) {
            if (value < 0) {
                return -1 * Integer.valueOf(Integer.toBinaryString(value * -1)) // toBinaryString with negative integer give errors
            } else {
                return Integer.valueOf(Integer.toBinaryString(value))
            }
        } else if (baseFrom.equals(OCT)) {
            var decimalValue = convertDec(OCT, value)
            if (value < 0) {
                return -1 * Integer.valueOf(Integer.toBinaryString(decimalValue * -1)) // toBinaryString with negative integer give errors
            } else {
                return Integer.valueOf(Integer.toBinaryString(decimalValue))
            }
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
        }

        firstNumber = firstNumber * 10 + i
        setValue(Integer.toString(firstNumber))

        digits++
    }

    private fun swapRegisters() {
        firstNumber += secondNumber
        secondNumber = firstNumber - secondNumber
        firstNumber -= secondNumber
        digits = 0
        lastIsOperation = true
    }

    private fun setClear() {
        mCallback!!.setClear("CE")
    }

    private fun setAllClear() {
        mCallback!!.setClear("AC")
    }

    private fun negateNumber() {
        firstNumber *= -1
        setValue(Integer.toString(firstNumber))
    }
}