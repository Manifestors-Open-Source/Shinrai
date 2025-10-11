package com.manifestors.shinrai.client.setting.settings

import com.manifestors.shinrai.client.setting.Setting

class DoubleSetting(
    name: String,
    current: Double,
    val min: Double,
    val max: Double,
    val step: Double
) : Setting<Double>(name, current) {

    var current: Double = current
        private set

    fun increment() {
        current = (current + step).coerceAtMost(max)
    }

    fun decrement() {
        current = (current - step).coerceAtLeast(min)
    }

    fun setValue(value: Double) {
        current = value.coerceIn(min, max)
    }
}
