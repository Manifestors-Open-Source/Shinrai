package com.manifestors.shinrai.client

import com.manifestors.shinrai.client.command.CommandManager
import com.manifestors.shinrai.client.event.EventManager
import com.manifestors.shinrai.client.module.ModuleManager
import com.manifestors.shinrai.client.ui.custom.ShinraiCustomizationScreen
import com.manifestors.shinrai.client.utils.LoggerInstance
import com.manifestors.shinrai.client.utils.MinecraftInstance
import com.manifestors.shinrai.client.utils.discord.RPCEngine
import com.manifestors.shinrai.client.utils.file.FileManager
import net.minecraft.text.Text
import net.minecraft.util.Formatting

object Shinrai : LoggerInstance, MinecraftInstance {

    const val NAME: String = "Shinrai"
    const val VERSION: String = "1.0.0"
    const val AUTHORS: String = "Manifestors"
    const val IN_DEV: Boolean = true

    lateinit var moduleManager: ModuleManager
    lateinit var eventManager: EventManager
    lateinit var commandManager: CommandManager

    fun initializeShinrai() {
        moduleManager = ModuleManager
        moduleManager.registerModules()
        eventManager = EventManager
        moduleManager.loadModulesFromJson()
        commandManager = CommandManager
        commandManager.registerCommands()
        RPCEngine.startRPC()
        FileManager.createDirectories()

        ShinraiCustomizationScreen.loadBackgroundFromJson()
    }

    fun shutdownShinrai() {
        logger.info("Saving modules...")
        moduleManager.saveModulesJson()
        logger.info("Modules saved, shutting down subsystems...")
        RPCEngine.shutdownRPC()
        logger.info("Goodbye!")
    }

    val fullVersion: String
        get() {
            val nameAndVersion = "$NAME $VERSION"
            return if (IN_DEV) "$nameAndVersion (Development)" else nameAndVersion
        }

    fun addChatMessage(message: String?) {
        val header = Formatting.RED.toString() + "" + Formatting.BOLD + NAME + Formatting.GRAY + " » "
        mc.inGameHud.chatHud.addMessage(Text.literal(header + message))
    }

}
