package com.manifestors.shinrai.client.module.modules.combat.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.command.ServerCommandSource;

import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.OpenOption;
import java.util.LinkedHashMap;

public abstract class ManifestorsCustomMidnightConfig {

    private static final LinkedHashMap<String, EntryInfo> entries = new LinkedHashMap<>();
    private static Path path;
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void init(String modid, Class<? extends ManifestorsCustomMidnightConfig> config) {
        path = PlatformFunctions.getConfigDirectory().resolve(modid + ".json");

        for(Field field : config.getFields()) {
            EntryInfo info = new EntryInfo(field, modid);
            try {
                info.defaultValue = field.get(null);
            } catch (IllegalAccessException ignored) {
            }
            entries.put(modid + ":" + field.getName(), info);
        }
        loadValuesFromJson(modid, config);
    }

    public static void loadValuesFromJson(String modid, Class<? extends ManifestorsCustomMidnightConfig> config) {
        try {
            ManifestorsCustomMidnightConfig loaded = gson.fromJson(Files.newBufferedReader(path), config);
            if (loaded != null) {
                for (Field field : config.getFields()) {
                    Object value = field.get(loaded);
                    field.set(null, value);
                }
            }
        } catch (Exception e) {
            write(modid, config);
        }

        entries.values().forEach((info) -> {
            if (info.field != null) {
                try {
                    info.value = info.field.get(null);
                } catch (IllegalAccessException ignored) {
                }
            }
        });
    }


    public static void write(String modid, Class<? extends ManifestorsCustomMidnightConfig> config) {
        try {
            if (!Files.exists(path, new LinkOption[0])) {
                Files.createFile(path);
            }
            Files.write(path, gson.toJson(config.getDeclaredConstructor().newInstance()).getBytes(), new OpenOption[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static class EntryInfo {
        public final Field field;
        public final String modid;
        public Object defaultValue;
        public Object value;

        public EntryInfo(Field field, String modid) {
            this.field = field;
            this.modid = modid;
        }
    }

    public static class PlatformFunctions {

        public static String getPlatformName() {
            return PlatformFunctionsImpl.getPlatformName();
        }


        public static Path getConfigDirectory() {
            return PlatformFunctionsImpl.getConfigDirectory();
        }


        public static boolean isClientEnv() {
            return PlatformFunctionsImpl.isClientEnv();
        }


        public static boolean isModLoaded(String modid) {
            return PlatformFunctionsImpl.isModLoaded(modid);
        }


        public static void registerCommand(LiteralArgumentBuilder<ServerCommandSource> command) {
            PlatformFunctionsImpl.registerCommand(command);
        }
    }

    public static class PlatformFunctionsImpl {
        public static String getPlatformName() {
            return "fabric";
        }

        public static Path getConfigDirectory() {
            return FabricLoader.getInstance().getConfigDir();
        }

        public static boolean isClientEnv() {
            return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
        }

        public static boolean isModLoaded(String modid) {
            return FabricLoader.getInstance().isModLoaded(modid);
        }

        public static void registerCommand(LiteralArgumentBuilder<ServerCommandSource> command) {
            CommandRegistrationCallback.EVENT.register((dispatcher, dedicated, registrationEnvironment) -> dispatcher.register(command));
        }
    }
}