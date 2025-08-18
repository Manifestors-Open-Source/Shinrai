package com.manifestors.shinrai.client.utils.discord;

import com.manifestors.shinrai.client.Shinrai;
import com.manifestors.shinrai.client.ui.title.ShinraiTitleScreen;
import com.manifestors.shinrai.client.utils.MinecraftInstance;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;

public class RPCEngine implements MinecraftInstance {

    private static boolean isRPCRunning = false;
    private static long gameStartedAt = 0L;

    public static String[] getOperatingSystemData(){
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            return new String[]{"win", "Windows"};
        } else if (os.contains("mac")) {
            return new String[]{"mac", "macOS"};
        } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
            return new String[]{"lin", "Linux"};
        }

        return new String[]{"organizationlogo", "Unknown OS"};
    }

    public static void startRPC() {
        DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().build();
        DiscordRPC.discordInitialize("849293997771456532", handlers, true);
        isRPCRunning = true;
        gameStartedAt = System.currentTimeMillis() / 1000;
        new Thread(() -> {
            while (isRPCRunning) {
                runRPC();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignored) {
                }
                DiscordRPC.discordRunCallbacks();
            }
        }, "Discord-RPC-Callback-Thread").start();
    }

    public static void shutdownRPC() {
        isRPCRunning = false;
        DiscordRPC.discordShutdown();
    }

    public static void runRPC() {
        String totalledModules = "Enabled " + Shinrai.INSTANCE.getModuleManager().getEnabledModules().size() + " of " + Shinrai.INSTANCE.getModuleManager().getModules().size() + " modules";

        DiscordRichPresence presence = new DiscordRichPresence.Builder(getState())
                .setDetails(totalledModules)
                .setBigImage("logo", Shinrai.INSTANCE.getFullVersion())
                .setSmallImage(getOperatingSystemData()[0], getOperatingSystemData()[1])
                .setStartTimestamps(gameStartedAt)
                .build();

        DiscordRPC.discordUpdatePresence(presence);
    }

    private static String getState() {
        if (mc.currentScreen instanceof ShinraiTitleScreen)
            return "In main menu";
        if (mc.currentScreen instanceof SelectWorldScreen)
            return "Selecting a world";
        if (mc.currentScreen instanceof MultiplayerScreen)
            return "Looking for servers";
        if (mc.player != null) {
            var server = mc.getServer();
            return mc.isIntegratedServerRunning() || server == null ? "Playing in a singleplayer world" : "Playing on " + server.getServerIp();
        }

        return "No activity";
    }

}
