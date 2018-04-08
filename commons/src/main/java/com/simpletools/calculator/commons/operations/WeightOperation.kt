package com.simpletools.calculator.commons.operations

object WeightOperation {
    var value: Double = 0.0
    var conversion: String = ""
    var weightMap = HashMap<String, Double>()
    
    init {
        weightMap.put("lb oz", 16.0)
        weightMap.put("lb lb", 1.0)
        weightMap.put("lb g", 453.592)
        weightMap.put("lb kg", 0.453592)
        weightMap.put("kg oz", 35.274)
        weightMap.put("kg lb", 2.20462)
        weightMap.put("kg g", 1000.0)
        weightMap.put("kg kg", 1.0)
        weightMap.put("oz oz", 1.0)
        weightMap.put("oz lb", 0.0625)
        weightMap.put("oz g", 28.3495)
        weightMap.put("oz kg", 0.0283495)
        weightMap.put("g oz", 0.035274)
        weightMap.put("g lb", 0.00220462)
        weightMap.put("g g", 1.0)
        weightMap.put("g kg", 0.001)
    }
    
    fun setParams(value: Double, conversion: String): WeightOperation? {
        WeightOperation.value = value
        WeightOperation.conversion = conversion
        return this
    }

    fun getResult(): Double {
        return value * weightMap[conversion]!!
    }
}