package com.simpletools.calculator.commons.operations

object TaxOperation {

    var value: Double = 0.0
    var location: String = ""
    var taxesMap = HashMap<String, Double>()

    init {
        taxesMap.put("British Columbia", 0.12)
        taxesMap.put("Alberta", 0.05)
        taxesMap.put("Saskatchewan", 0.11)
        taxesMap.put("Manitoba", 0.13)
        taxesMap.put("Ontario", 0.13)
        taxesMap.put("Quebec", 0.14975)
        taxesMap.put("New Brunswick", 0.15)
        taxesMap.put("Nova Scotia", 0.15)
        taxesMap.put("Prince Edward Island", 0.15)
        taxesMap.put("Newfoundland and Labrador", 0.15)
        taxesMap.put("Northwest Territories", 0.05)
        taxesMap.put("Nunavut", 0.05)
        taxesMap.put("Yukon", 0.05)
    }

    fun setParams(value: Double, location: String): TaxOperation? {
        this.value = value
        this.location = location
        return this
    }

    fun getResult(): Double {
        return value + value * taxesMap[location]!!
    }

    fun getFormula(): String {
        return "Tax in" + location + " is " + taxesMap[location]
    }

    fun getTaxRate(province: String): Double {
        return taxesMap[province]!!
    }
}
