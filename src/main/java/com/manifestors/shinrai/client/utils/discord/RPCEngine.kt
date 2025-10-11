package com.manifestors.shinrai.client.utils.discord

import com.manifestors.shinrai.client.Shinrai.moduleManager
import com.manifestors.shinrai.client.Shinrai.fullVersion
import com.manifestors.shinrai.client.ui.title.ShinraiTitleScreen
import com.manifestors.shinrai.client.utils.MinecraftInstance
import net.arikia.dev.drpc.DiscordEventHandlers
import net.arikia.dev.drpc.DiscordRPC
import net.arikia.dev.drpc.DiscordRichPresence
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen
import net.minecraft.client.gui.screen.world.SelectWorldScreen
import java.util.*

object RPCEngine : MinecraftInstance {

    private var isRPCRunning = false
    private var gameStartedAt = 0L

    val operatingSystemData: Array<String>
        get() {
            val os = System.getProperty("os.name").lowercase(Locale.getDefault())
            if (os.contains("win")) {
                return arrayOf("win", "Windows")
            } else if (os.contains("mac")) {
                return arrayOf("mac", "macOS")
            } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
                return arrayOf("lin", "Linux")
            }

            return arrayOf("organizationlogo", "Unknown OS")
        }

    fun startRPC() {
        val handlers = DiscordEventHandlers.Builder().build()
        DiscordRPC.discordInitialize("849293997771456532", handlers, true)
        isRPCRunning = true
        gameStartedAt = System.currentTimeMillis() / 1000
        Thread(Runnable {
            while (isRPCRunning) {
                runRPC()
                try {
                    Thread.sleep(500)
                } catch (ignored: InterruptedException) {
                }
                DiscordRPC.discordRunCallbacks()
            }
        }, "Discord-RPC-Callback-Thread").start()
    }

    fun shutdownRPC() {
        isRPCRunning = false
        DiscordRPC.discordShutdown()
    }

    fun runRPC() {
        val totalledModules =
            "Enabled " + moduleManager.enabledModules.size + " of " + moduleManager
                .modules.size + " modules"

        val presence = DiscordRichPresence.Builder(state)
            .setDetails(totalledModules)
            .setBigImage("logo", fullVersion)
            .setSmallImage(operatingSystemData[0], operatingSystemData[1])
            .setStartTimestamps(gameStartedAt)
            .build()

        DiscordRPC.discordUpdatePresence(presence)
    }

    private val state: String
        get() {
            if (mc.currentScreen is ShinraiTitleScreen) return "In main menu"
            if (mc.currentScreen is SelectWorldScreen) return "Selecting a world"
            if (mc.currentScreen is MultiplayerScreen) return "Looking for servers"
            if (mc.player != null) {
                val server = mc.server
                return if (mc.isIntegratedServerRunning || server == null) "Playing in a singleplayer world" else "Playing on " + server.serverIp
            }

            return "No activity"
        }
}
