package com.manifestors.shinrai.client.setting.settings

import com.manifestors.shinrai.client.Shinrai
import com.manifestors.shinrai.client.setting.Setting

class ChoiceSetting(
    name: String,
    private var defaultChoice: String,
    vararg choices: String
) : Setting<String>(name, defaultChoice) {

    private val choicesList = buildList {
        add(defaultChoice)
        addAll(choices)
    }.distinct().toMutableList()

    fun getChoice(choiceName: String?): String =
        choicesList.firstOrNull { it.equals(choiceName, ignoreCase = true) } ?: "unknown"

    fun setDefaultChoice(choiceName: String?) {
        if (choiceName != null && choiceName in choicesList)
            defaultChoice = choiceName
        else
            Shinrai.logger.warn("Invalid choice: {}", choiceName)
    }

    fun getChoices(): List<String> = choicesList.toList()

    fun getDefaultChoice(): String = defaultChoice
}
