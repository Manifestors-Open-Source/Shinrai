/*
 *
 *  * Copyright © 2026 Manifestors Open Source
 *  * License: GPL-3.0
 *  *
 *  * All code in this project is the property of the Manifestors Open Source team
 *  * and its contributors. If you use this code in any project, please provide proper attribution
 *  * and release your project under the GPL-3.0 license as well.
 *  *
 *  * For more details, see: https://www.gnu.org/licenses/gpl-3.0.en.html
 *
 */

package com.manifestors.shinrai.client.features.command.commands

import com.manifestors.shinrai.client.Shinrai.addChatMessage
import com.manifestors.shinrai.client.Shinrai.moduleManager
import com.manifestors.shinrai.client.features.command.Command
import com.manifestors.shinrai.client.utils.input.GLFWUtil.getKeyIndex
import java.util.*

class BindCommand : Command(
    "bind",
    "Change our reset modules keybind.",
    "moduleName, key",
    "b") {
    override fun onCommandExecuted(args: Array<String>): Boolean {
        if (args.size < 3) return false

        val (moduleName, keyInput) = args.drop(1)

        moduleManager.getModuleByName(moduleName)?.let {
            if (keyInput.equals("none", ignoreCase = true)) {
                it.keyCode = 0
                addChatMessage("Unbound module " + it.name + ".")
            } else {
                val keyName = keyInput.uppercase(Locale.getDefault())
                val key = getKeyIndex(keyName)
                if (key != -1) {
                    it.keyCode = key
                    addChatMessage(it.name + " bound to " + keyName + ".")
                } else addChatMessage("Invalid key '$keyInput'.")
            }
        } ?: sendNotFound("Module '$moduleName'")

        return true
    }
}
