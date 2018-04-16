package com.simplemobiletools.calculator.helpers

import android.content.Context
import com.simpletools.calculator.commons.helpers.Calculator
import com.simpletools.calculator.commons.operations.WeightOperation
import com.simpletools.calculator.commons.operations.LengthOperation
import com.simpletools.calculator.commons.operations.VolumeOperation

class ConversionCalculatorImpl(calculator: Calculator, override val context: Context) : BaseCalculatorImpl(calculator, context) {
    private var mCallback: Calculator? = calculator

    override fun handleClear() {
        number = ""
        decimalClicked = false
        decimalCounter = 0
        setValue("0")
    }

    override fun addDigit(i: Int) {
        if (number != "" || i != 0) {
            number = number + i
            setValue(number)
        }
    }

    override fun overwriteNumber(newNumber: Double) {
        number = ""
        decimalClicked = false
        decimalCounter = 0
        setValue(newNumber.toString())
    }

    override fun handleDelete() {
        if (number.length <= 1) {
            return handleClear()
        } else if (decimalClicked && number.indexOf('.') == number.length - 2) {
            number = number.dropLast(2)
            decimalClicked = false
        } else {
            number = number.dropLast(1)
        }
        setValue(number)
    }

    fun performWeightConversion(from: String, to: String) {
        overwriteNumber(WeightOperation.setParams(getResult(), "$from $to")!!.getResult())
    }

    fun performLengthConversion(from: String, to: String) {
        overwriteNumber(LengthOperation.setParams(getResult(), "$from $to")!!.getResult())
    }

    fun performVolumeOperation(from: String, to: String) {
        overwriteNumber(VolumeOperation.setParams(getResult(), "$from $to")!!.getResult())
    }
}