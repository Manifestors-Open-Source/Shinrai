package com.manifestors.shinrai.client.command.commands

import com.manifestors.shinrai.client.Shinrai.addChatMessage
import com.manifestors.shinrai.client.Shinrai.moduleManager
import com.manifestors.shinrai.client.command.Command
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
