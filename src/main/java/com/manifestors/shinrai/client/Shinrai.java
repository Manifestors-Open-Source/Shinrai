package com.manifestors.shinrai.client;

import com.manifestors.shinrai.client.command.CommandManager;
import com.manifestors.shinrai.client.event.EventManager;
import com.manifestors.shinrai.client.module.ModuleManager;

import com.manifestors.shinrai.client.utils.discord.RPCEngine;
import com.manifestors.shinrai.client.module.modules.combat.SwordBlockingClient;
import com.manifestors.shinrai.client.ui.custom.ShinraiCustomizationScreen;
import com.manifestors.shinrai.client.utils.LoggerInstance;
import com.manifestors.shinrai.client.utils.MinecraftInstance;
import com.manifestors.shinrai.client.utils.file.FileManager;
import lombok.Getter;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

@Getter
public enum Shinrai implements LoggerInstance, MinecraftInstance {

    INSTANCE;

    public static final String NAME = "Shinrai";
    public static final String VERSION = "1.0.0";
    public static final String AUTHORS = "Manifestors";
    public static final boolean IN_DEV = true;

    private ModuleManager moduleManager;
    private EventManager eventManager;
    private CommandManager commandManager;

    public void initializeShinrai() {
        moduleManager = new ModuleManager();
        moduleManager.registerModules();
        eventManager = new EventManager();
        moduleManager.loadModulesFromJson();
        commandManager = new CommandManager();
        commandManager.registerCommands();
        SwordBlockingClient.init();
        RPCEngine.startRPC();
        FileManager.createDirectories();

        new ShinraiCustomizationScreen().loadBackgroundFromJson();
    }

    public void shutdownShinrai() {
        logger.info("Saving modules...");
        moduleManager.saveModulesJson();
        logger.info("Modules saved, shutting down subsystems...");
        RPCEngine.shutdownRPC();
        logger.info("Goodbye!");
    }

    public String getFullVersion() {
        String nameAndVersion = NAME + " " + VERSION;
        return IN_DEV ? nameAndVersion + " (Development)" : nameAndVersion;
    }

    public void addChatMessage(String message) {
        var header = Formatting.RED + "" + Formatting.BOLD + Shinrai.NAME + Formatting.GRAY + " » ";
        mc.inGameHud.getChatHud().addMessage(Text.literal(header + message));
    }

}
