package com.manifestors.shinrai.client.utils;

import com.manifestors.shinrai.client.utils.Discord.RPCEngine;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;

public class Trackers {
    private static MinecraftClient mc = MinecraftClient.getInstance();
    private static String lastWorldName = "";

    public static void init() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (mc.getServer() != null) {
                String worldName = mc.getServer().getSaveProperties().getLevelName();

                if (!worldName.equals(lastWorldName)) {
                    lastWorldName = worldName;
                    onWorldChanged(worldName);
                }
            }
        });
    }

    public static void onWorldChanged(String worldName) {
        RPCEngine.WorlsRPC(worldName);
    }
}
