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

        val moduleName = args[1]
        val keyInput = args[2]

        val module = moduleManager.getModuleByName(moduleName)

        if (module != null) {
            if (keyInput.equals("none", ignoreCase = true)) {
                module.keyCode = 0
                addChatMessage("Unbound module " + module.name + ".")
            } else {
                val keyName = keyInput.uppercase(Locale.getDefault())
                val key = getKeyIndex(keyName)
                if (key != -1) {
                    module.keyCode = key
                    addChatMessage(module.name + " bound to " + keyName + ".")
                } else addChatMessage("Invalid key $keyInput.")
            }
        } else {
            sendNotFound("Module '$moduleName'")
        }

        return true
    }
}
