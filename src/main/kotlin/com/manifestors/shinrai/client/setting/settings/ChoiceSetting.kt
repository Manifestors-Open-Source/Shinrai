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

import com.manifestors.shinrai.client.Shinrai
import com.manifestors.shinrai.client.setting.Setting

class ChoiceSetting(
    name: String,
    current: String,
    vararg choices: String
) : Setting<String>(name, current) {

    var currentChoice: String = current
        set(value) {
            if (field in choicesList) {
                field = value
                super.current = field
            }
            else
                Shinrai.logger.warn("Invalid choice: {}", field)
        }

    private val choicesList = buildList {
        add(currentChoice)
        addAll(choices)
    }.distinct().toMutableList()

    fun getChoice(choiceName: String?): String =
        choicesList.firstOrNull { it.replace(" ", "").equals(choiceName, ignoreCase = true) } ?: "unknown"

    fun getChoices(): List<String> = choicesList.toList()
}
