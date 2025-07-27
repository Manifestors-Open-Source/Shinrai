package com.manifestors.shinrai.file;

import com.manifestors.shinrai.client.Shinrai;
import com.manifestors.shinrai.file.settings.modules.ModuleSettings;

import java.io.File;
import java.io.IOException;

public final class FileManager {

    private static final File rootDirectory = new File(Shinrai.NAME);
    private static final File settingsDirectory = new File(rootDirectory, "settings");
    private static final File settingsFile = new File(settingsDirectory, "module_settings.shinrai");
    private static final ModuleSettings moduleSettings = new ModuleSettings();

    public static void initializeFiles() {
        try {
            if (settingsDirectory.mkdirs() && settingsFile.createNewFile()) {
                loadSettings();
                Shinrai.logger.info("File system initializing successfully.");
            }
        } catch (IOException e) {
            Shinrai.logger.error("File system can not initialized.", e);
        }
    }

    public static void saveSettings() {
        moduleSettings.saveToJson(settingsFile);
    }

    public static void loadSettings() {
        moduleSettings.readFromJson(settingsFile);
    }

}
