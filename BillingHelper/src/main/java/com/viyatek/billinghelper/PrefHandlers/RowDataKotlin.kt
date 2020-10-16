package com.viyatek.facefind.helpers

class RowDataKotlin(var tag:String?, var value:String) {

    fun getStringValue(): String? {
        return value
    }

    fun getIntegerValue(): Int {
        if (value == "") {
            return 0
        }
        var returnValue = 0
        returnValue = try {
            value.toInt()
        } catch (e: NumberFormatException) {
            0
        }
        return returnValue
    }

    fun getFloatValue(): Float {
        if (value == null || value.trim { it <= ' ' } == "") {
            return 0f
        }
        var returnValue = 0f
        returnValue = try {
            java.lang.Float.valueOf(value)
        } catch (e: java.lang.NumberFormatException) {
            0f
        }
        return returnValue
    }
}