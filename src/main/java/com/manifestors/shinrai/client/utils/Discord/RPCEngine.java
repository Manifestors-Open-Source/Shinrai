package com.manifestors.shinrai.client.utils.Discord;

import com.manifestors.shinrai.client.Shinrai;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.arikia.dev.drpc.DiscordEventHandlers;

public class RPCEngine {
    private static String SelectedOperatingSystem;
    public static void getOperatingSystem(){
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            SelectedOperatingSystem = "win";
        } else if (os.contains("mac")) {
            SelectedOperatingSystem = "mac";
        } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
            SelectedOperatingSystem = "lin";
        } else {
            SelectedOperatingSystem = "organizationlogo";
        }

    }
    public static void StartRPC() {
        DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().build();
        DiscordRPC.discordInitialize("849293997771456532", handlers, true);

        DiscordRichPresence presence = new DiscordRichPresence.Builder("On Main Menu")
                .setDetails("No Activity")
                .setBigImage("logo","Shinrai Minecraft Client")
                .setSmallImage(SelectedOperatingSystem,SelectedOperatingSystem)
                .setStartTimestamps(System.currentTimeMillis())
                .build();

        DiscordRPC.discordUpdatePresence(presence);


        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                DiscordRPC.discordRunCallbacks();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }, "Discord-RPC-Callback-Thread").start();
    }

    public static void WorlsRPC(String WorldName) {
        DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().build();
        DiscordRPC.discordInitialize("849293997771456532", handlers, true);

        DiscordRichPresence presence = new DiscordRichPresence.Builder("Playing in a singleplayer world")
                .setDetails(WorldName + "("+ Shinrai.NAME + " " + Shinrai.VERSION +")")
                .setBigImage("shinrailogominecraft",WorldName)
                .setSmallImage(SelectedOperatingSystem,SelectedOperatingSystem)
                .setStartTimestamps(System.currentTimeMillis())
                .build();

        DiscordRPC.discordUpdatePresence(presence);


        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                DiscordRPC.discordRunCallbacks();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }, "Discord-RPC-Callback-Thread").start();
    }
}
