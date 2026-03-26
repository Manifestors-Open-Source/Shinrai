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

package com.manifestors.shinrai.client.features.command

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
