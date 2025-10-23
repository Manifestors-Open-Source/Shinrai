package com.manifestors.shinrai.client.command.commands

import com.manifestors.shinrai.client.Shinrai.addChatMessage
import com.manifestors.shinrai.client.Shinrai.moduleManager
import com.manifestors.shinrai.client.command.Command

class ToggleCommand : Command(
    commandName = "toggle",
    commandDescription = "Toggles a module by name.",
    validParameters = "module",
    "t"
) {

    override fun onCommandExecuted(args: Array<String>): Boolean {
        if (args.size < 2) return false

        val moduleName = args[1]
        moduleManager.getModuleByName(moduleName)?.let {
           it.toggleModule()
           addChatMessage("${it.name} has been ${if (it.enabled) "enabled" else "disabled"}.")
        } ?: sendNotFound("Module '$moduleName'")

        return true
    }
}
