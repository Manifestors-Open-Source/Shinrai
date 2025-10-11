package com.manifestors.shinrai.client.setting.settings

import com.manifestors.shinrai.client.setting.Setting

class IntegerSetting(
    name: String,
    current: Int,
    val min: Int,
    val max: Int,
    val step: Int
) : Setting<Int>(name, current) {

    var current: Int = current
        private set

    fun increment() {
        current = (current + step).coerceAtMost(max)
    }

    fun decrement() {
        current = (current - step).coerceAtLeast(min)
    }

    fun setValue(value: Int) {
        current = value.coerceIn(min, max)
    }
}
