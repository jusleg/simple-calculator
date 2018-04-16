package com.simpletools.calculator.commons.operations

object VolumeOperation {
    private var value: Double = 0.0
    private var conversion: String = ""
    private var VolumeMap = HashMap<String, Double>()

    init {
        VolumeMap.put("Ml Ml", 1.0)
        VolumeMap.put("Ml L", 0.001)
        VolumeMap.put("Ml Quart", 0.00105669)
        VolumeMap.put("Ml Gallon", 0.000264172)
        VolumeMap.put("L Ml", 1000.0)
        VolumeMap.put("L L", 1.0)
        VolumeMap.put("L Quart", 1.05669)
        VolumeMap.put("L Gallon", 0.264172)
        VolumeMap.put("Quart Ml", 946.353)
        VolumeMap.put("Quart L", 0.946353)
        VolumeMap.put("Quart Quart", 1.0)
        VolumeMap.put("Quart Gallon", 0.25)
        VolumeMap.put("Gallon Ml", 3785.41)
        VolumeMap.put("Gallon L", 3.78541)
        VolumeMap.put("Gallon Quart", 4.0)
        VolumeMap.put("Gallon Gallon", 1.0)
    }

    fun setParams(value: Double, conversion: String): VolumeOperation? {
        VolumeOperation.value = value
        VolumeOperation.conversion = conversion
        return this
    }

    fun getResult(): Double {
        return value * VolumeMap[conversion]!!
    }
}