package com.manifestors.shinrai.client.command

import com.manifestors.shinrai.client.Shinrai.addChatMessage
import com.manifestors.shinrai.client.utils.LoggerInstance
import com.manifestors.shinrai.client.utils.MinecraftInstance

abstract class Command(
    private val commandName: String,
    private val commandDescription: String,
    private val validParameters: String,
    vararg val aliases: String
) : LoggerInstance, MinecraftInstance {

    abstract fun onCommandExecuted(args: Array<String>): Boolean

    fun sendUsage() {
        val cmdName = if (aliases.isNotEmpty()) {
            CommandManager.PREFIX + "<${allCommandNames.joinToString("/")}>"
        } else {
            commandName
        }
        addChatMessage("Usage: $cmdName [$validParameters]")
    }

    val allCommandNames: List<String>
        get() = listOf(commandName) + aliases.toList()

    fun sendNotFound(findingObject: String) {
        addChatMessage("$findingObject not found.")
    }
}
