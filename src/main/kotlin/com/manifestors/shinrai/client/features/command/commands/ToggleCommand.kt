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

package com.manifestors.shinrai.client.features.command.commands

import com.manifestors.shinrai.client.Shinrai.addChatMessage
import com.manifestors.shinrai.client.features.command.Command
import com.manifestors.shinrai.client.features.module.ModuleManager

class ToggleCommand : Command(
    commandName = "toggle",
    commandDescription = "Toggles a module by name.",
    validParameters = "module",
    "t"
) {

    override fun onCommandExecuted(args: Array<String>): Boolean {
        if (args.size < 2) return false

        val moduleName = args[1]
        ModuleManager.getModuleByName(moduleName)?.let {
           it.toggleModule()
           addChatMessage("${it.name} has been ${if (it.enabled) "enabled" else "disabled"}.")
        } ?: sendNotFound("Module '$moduleName'")

        return true
    }
}
