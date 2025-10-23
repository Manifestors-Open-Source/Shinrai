package com.manifestors.shinrai.client.setting.settings

import com.manifestors.shinrai.client.setting.Setting

class DoubleSetting(
    name: String,
    current: Double,
    val min: Double,
    val max: Double,
    val step: Double
) : Setting<Double>(name, current) {

    var currentValue: Double = current
        set(value) {
            field = value.coerceIn(min, max)
            super.current = field
        }

    fun change(by: Double) {
        currentValue += by
    }

    fun increment() = change(step)
    fun decrement() = change(-step)

}
