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

class BitwiseCalculatorImpl(calculator: Calculator, override val context: Context) : BaseCalculatorImpl(calculator, context) {
    private var firstNumber: Long = 0
    private var secondNumber: Long = 0
    private var operator: String? = ""
    private var lastOperator: String? = ""
    private var lastOperand: Long = 0
    private var secondNumberSet: Boolean = false
    private var digits = 0
    private var lastIsOperation: Boolean = false
    private var currentBase: String? = DEC

    init {
        resetValues()
        setValue("0")
        setFormula("")
    }

    private fun addDigitLong(i: Long) {
        if (operator != "") {
            secondNumberSet = true
            setClear()
        }

        firstNumber = firstNumber * 10 + i
        setValue(firstNumber.toString())

        digits++

        if (digits > 16) {
            setValue("OVERFLOW")
        }
    }

    override fun numpadClicked(id: Int) {
        when (id) {
            R.id.btn_decimal -> decimalClick()
            R.id.btn_0 -> addDigitLong(0)
            R.id.btn_1 -> addDigitLong(1)
            R.id.btn_2 -> addDigitLong(2)
            R.id.btn_3 -> addDigitLong(3)
            R.id.btn_4 -> addDigitLong(4)
            R.id.btn_5 -> addDigitLong(5)
            R.id.btn_6 -> addDigitLong(6)
            R.id.btn_7 -> addDigitLong(7)
            R.id.btn_8 -> addDigitLong(8)
            R.id.btn_9 -> addDigitLong(9)
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

    override fun handleClear() {
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
        try {
            setAllClear()
            resetValues()
            lastIsOperation = false
            if (currentBase.equals(BIN)) {
                firstNumber = convertBin(DEC, operation.getResult())
                setValue(firstNumber.toString())
                setFormula(operation.getBinaryFormula())
            } else if (currentBase.equals(OCT)) {
                firstNumber = convertOct(DEC, operation.getResult())
                setValue(firstNumber.toString())
                setFormula(operation.getOctalFormula())
            } else {
                firstNumber = operation.getResult()
                setValue(firstNumber.toString())
                setFormula(operation.getDecimalFormula())
            }
        } catch (e: NumberFormatException) {
            setValue("OVERFLOW")
        }
    }

    fun convertToDecimal(baseFrom: String) {
        currentBase = DEC
        try {
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
        } catch (e: NumberFormatException) {
            setValue("OVERFLOW")
        }
    }

    fun convertToOctal(baseFrom: String) {
        try {
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
        } catch (e: NumberFormatException) {
            setValue("OVERFLOW")
        }
    }

    fun convertToBinary(baseFrom: String) {
        try {
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
        } catch (e: NumberFormatException) {
            setValue("OVERFLOW")
        }
    }

    private fun convertDec(baseFrom: String, value: Long): Long {
        if (baseFrom.equals(OCT)) {
            if (value < 0) {
                return (-1 * Integer.valueOf((value*-1).toString(), 8)).toLong()
            } else {
                return (Integer.valueOf(value.toString(), 8)).toLong()
            }
        } else if (baseFrom.equals(BIN)) {
            if (value < 0) {
                return (-1 * Integer.valueOf((value*-1).toString(), 2)).toLong()
            } else {
                return (Integer.valueOf(value.toString(), 2)).toLong()
            }
        } else return 0
    }

    private fun convertOct(baseFrom: String, value: Long): Long {
        if (baseFrom.equals(DEC)) {
            if (value < 0) {
                return -1 * ((value * -1).toString(8)).toLong() // toOctalString with negative Long give errors
            } else {
                return (value.toString(8)).toLong()
            }
        } else if (baseFrom.equals(BIN)) {
            var decimalValue = convertDec(BIN, value)
            if (value < 0) {
                return -1 * ((decimalValue * -1).toString(8)).toLong() // toOctalString with negative Long give errors
            } else {
                return decimalValue.toString(8).toLong()
            }
        } else return 0
    }

    private fun convertBin(baseFrom: String, value: Long): Long {
        if (baseFrom.equals(DEC)) {
            if (value < 0) {
                return -1 * ((value * -1).toString(2)).toLong() // toBinaryString with negative Long give errors
            } else {
                return value.toString(2).toLong()
            }
        } else if (baseFrom.equals(OCT)) {
            var decimalValue = convertDec(OCT, value)
            if (value < 0) {
                return -1 * ((decimalValue * -1).toString(2)).toLong() // toBinaryString with negative Long give errors
            } else {
                return decimalValue.toString(2).toLong()
            }
        } else return 0
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
        setValue(firstNumber.toString())
    }
}