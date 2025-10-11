package com.manifestors.shinrai.client.command

import com.manifestors.shinrai.client.Shinrai
import com.manifestors.shinrai.client.Shinrai.addChatMessage
import com.manifestors.shinrai.client.command.commands.BindCommand
import com.manifestors.shinrai.client.command.commands.ToggleCommand
import com.manifestors.shinrai.client.event.events.player.ChatMessageSendEvent
import java.util.concurrent.CopyOnWriteArrayList

object CommandManager {

    private val commands = CopyOnWriteArrayList<Command>()

    const val PREFIX: String = "."

    fun registerCommands() {
        try {
            Shinrai.logger.info("Loading commands...")
            commands.add(BindCommand())
            commands.add(ToggleCommand())
            Shinrai.logger.info("Loaded ${commands.size} commands.")
        } catch (e: Exception) {
            Shinrai.logger.error("Can't load commands: ", e)
        }
    }

    fun processCommands(event: ChatMessageSendEvent) {
        if (!event.message.startsWith(PREFIX)) return

        val args = event.message.split("\\s+".toRegex()).toTypedArray()
        event.cancelled = true

        val command = commands.firstOrNull { isValidCommand(args[0], it.allCommandNames) }
        if (command != null) {
            if (!command.onCommandExecuted(args)) command.sendUsage()
        } else {
            addChatMessage("Unknown command. If you forget any command, type .help for help.")
        }
    }

    private fun isValidCommand(context: String, commandNames: List<String>): Boolean {
        return commandNames.any { name -> context.startsWith("$PREFIX$name") }
    }
}
