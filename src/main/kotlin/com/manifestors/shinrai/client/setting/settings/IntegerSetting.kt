package com.manifestors.shinrai.client.setting.settings

import com.manifestors.shinrai.client.setting.Setting

class IntegerSetting(
    name: String,
    current: Int,
    val min: Int,
    val max: Int,
    val step: Int
) : Setting<Int>(name, current) {

    var currentValue: Int = current
        set(value) {
            field = value.coerceIn(min, max)
            super.current = field
        }

    fun change(by: Int) {
        currentValue += by
    }

    fun increment() = change(step)
    fun decrement() = change(-step)

}
