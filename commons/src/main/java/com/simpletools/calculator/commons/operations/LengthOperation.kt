package com.simpletools.calculator.commons.operations

object LengthOperation {
    private var value: Double = 0.0
    private var conversion: String = ""
    private var LengthMap = HashMap<String, Double>()

    init {
        LengthMap.put("cm cm", 1.0)
        LengthMap.put("cm m", 0.01)
        LengthMap.put("cm in", 0.393701)
        LengthMap.put("cm ft", 0.0328084)
        LengthMap.put("m cm", 100.0)
        LengthMap.put("m m", 1.0)
        LengthMap.put("m in", 39.3701)
        LengthMap.put("m ft", 3.28084)
        LengthMap.put("in cm", 2.54)
        LengthMap.put("in m", 0.0254)
        LengthMap.put("in in", 1.0)
        LengthMap.put("in ft", 0.0833333)
        LengthMap.put("ft cm", 30.48)
        LengthMap.put("ft m", 0.3048)
        LengthMap.put("ft in", 12.0)
        LengthMap.put("ft ft", 1.0)
    }

    fun setParams(value: Double, conversion: String): LengthOperation? {
        LengthOperation.value = value
        LengthOperation.conversion = conversion
        return this
    }

    fun getResult(): Double {
        return value * LengthMap[conversion]!!
    }
}