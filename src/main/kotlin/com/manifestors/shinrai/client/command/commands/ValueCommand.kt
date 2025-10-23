package com.manifestors.shinrai.client.command.commands

import com.manifestors.shinrai.client.Shinrai.addChatMessage
import com.manifestors.shinrai.client.Shinrai.moduleManager
import com.manifestors.shinrai.client.command.Command
import com.manifestors.shinrai.client.setting.Setting
import com.manifestors.shinrai.client.setting.settings.*

class ValueCommand : Command(
    "value",
    "Change module values by input.",
    "moduleName, settingName, newValue",
    "v"
) {

    override fun onCommandExecuted(args: Array<String>): Boolean {
        if (args.size < 4) return false

        val (moduleName, settingName, newValue) = args.drop(1)
        val module = moduleManager.getModuleByName(moduleName) ?: return true.also {
            addChatMessage("Module '$moduleName' not found.")
        }

        if (module.settings.isEmpty())
            return addChatMessage("This module has no settings.").let { true }

        val setting = module.settings.find { it.settingName?.replace(" ", "").equals(settingName, true) } ?: return true.also {
            addChatMessage("Setting '$settingName' not found in module '$moduleName'.")
        }

        val success = when (setting) {
            is IntegerSetting -> newValue.toIntOrNull()?.let { setting.currentValue = it; true } ?: false
            is DoubleSetting -> newValue.toDoubleOrNull()?.let { setting.currentValue = it; true } ?: false
            is ChoiceSetting -> if (setting.getChoice(newValue.lowercase()) != "unknown") {
                setting.currentChoice = newValue
                true
            } else false
            is BooleanSetting -> {
                setting.current = when (newValue.lowercase()) {
                    "true", "yes", "open" -> true
                    "false", "no", "close" -> false
                    else -> setting.current
                }
                true
            }
            else -> false
        }
        if (!success)
            sendValueUsage(setting)
        else
            addChatMessage("Value changed successfully.")

        return true
    }

    private fun sendValueUsage(setting: Setting<*>) = addChatMessage(
        when (setting) {
            is DoubleSetting -> "You can only enter numbers like 10 or 8.5."
            is IntegerSetting -> "You can only enter integer numbers like 5 or 10."
            is ChoiceSetting -> "This value doesn't exist in this setting."
            is BooleanSetting -> "You can only enter logical values like true or false."
            else -> "Invalid value."
        }
    )
}
