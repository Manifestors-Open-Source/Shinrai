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

package com.manifestors.shinrai.client

import com.manifestors.shinrai.client.features.command.CommandManager
import com.manifestors.shinrai.client.features.module.ModuleManager
import com.manifestors.shinrai.client.features.screen.customization.ShinraiCustomizationScreen
import com.manifestors.shinrai.client.utils.LoggerInstance
import com.manifestors.shinrai.client.utils.MinecraftInstance
import com.manifestors.shinrai.client.file.FileManager
import com.manifestors.shinrai.client.imgui.manager.ImGuiManager
import net.minecraft.text.Text
import net.minecraft.util.Formatting

object Shinrai : LoggerInstance, MinecraftInstance {

    const val NAME: String = "Shinrai"
    const val VERSION: String = "1.0.0"
    const val AUTHORS: String = "Manifestors"
    const val IN_DEV: Boolean = true

    fun initializeShinrai() {
        ModuleManager.registerModules()
        ModuleManager.loadModulesFromJson()
        CommandManager.registerCommands()
        ImGuiManager.init(mc.window.handle)

        FileManager.createDirectories()
        ShinraiCustomizationScreen.loadBackgroundFromJson()
    }

    fun shutdownShinrai() {
        logger.info("Saving modules...")
        ModuleManager.saveModulesJson()
        logger.info("Modules saved, shutting down subsystems...")
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
