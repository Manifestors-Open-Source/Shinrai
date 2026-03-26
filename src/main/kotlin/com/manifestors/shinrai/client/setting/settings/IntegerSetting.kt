/*
 *
 * Copyright © 2026 Manifestors Open Source
 * License: GPL-3.0
 * All code in this project is the property of the Manifestors Open Source team
 * and its contributors. If you use this code in any project, please provide proper attribution
 * and release your project under the GPL-3.0 license as well.
 * For more details, see: https://www.gnu.org/licenses/gpl-3.0.en.html
 *
 */

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
