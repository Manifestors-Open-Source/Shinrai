package com.manifestors.shinrai.client;

import com.manifestors.shinrai.client.event.EventManager;
import com.manifestors.shinrai.client.module.ModuleManager;
import com.manifestors.shinrai.client.utils.LoggerInstance;
import lombok.Getter;

@Getter
public enum Shinrai implements LoggerInstance {

    INSTANCE;

    public static final String NAME = "Shinrai";
    public static final String VERSION = "1.0.0";
    public static final String AUTHORS = "Manifestors";
    public static final boolean IN_DEV = true;

    private ModuleManager moduleManager;
    private EventManager eventManager;

    public void initializeShinrai() {
        moduleManager = new ModuleManager();
        moduleManager.registerModules();
        eventManager = new EventManager();
    }

    public void shutdownShinrai() {

    }

    public String getFullVersion() {
        String nameAndVersion = NAME + " " + VERSION;
        return IN_DEV ? nameAndVersion + " (Development)" : nameAndVersion;
    }

}
