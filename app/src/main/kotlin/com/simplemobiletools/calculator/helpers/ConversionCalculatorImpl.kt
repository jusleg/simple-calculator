package com.simplemobiletools.calculator.helpers

import android.content.Context
import com.simpletools.calculator.commons.helpers.Calculator
import com.simpletools.calculator.commons.operations.WeightOperation
import com.simpletools.calculator.commons.operations.LengthOperation
import com.simpletools.calculator.commons.operations.VolumeOperation

class ConversionCalculatorImpl(calculator: Calculator, override val context: Context) : BaseCalculatorImpl(calculator, context) {
    private var mCallback: Calculator? = calculator

    fun performWeightConversion(from: String, to: String) {
        overwriteNumber(WeightOperation.setParams(getResult(), "$from $to")!!.getResult())
    }

    fun performLengthConversion(from: String, to: String) {
        overwriteNumber(LengthOperation.setParams(getResult(), "$from $to")!!.getResult())
    }

    fun performTempOperation(from: String, to: String) {
        overwriteNumber(VolumeOperation.setParams(getResult(), "$from $to")!!.getResult())
    }
}