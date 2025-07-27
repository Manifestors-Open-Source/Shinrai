package com.manifestors.shinrai.file.settings.modules;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.manifestors.shinrai.client.Shinrai;
import com.manifestors.shinrai.client.module.Module;
import com.manifestors.shinrai.file.settings.ISettings;
import org.jetbrains.annotations.NotNull;

import java.io.*;

public class ModuleSettings  implements ISettings {

    @Override
    public void saveToJson(@NotNull File file) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            for (Module module : Shinrai.INSTANCE.getModuleManager().getModules()) {
                var modulesJson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(module);
                writer.print(modulesJson);
            }

        } catch (IOException e) {
            Shinrai.logger.error(e);
        }
    }

    @Override
    public void readFromJson(@NotNull File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            var module = new Gson().fromJson(reader, Module.class);
            var moduleJson = JsonParser.parseReader(reader).getAsJsonObject();

            if (!moduleJson.isEmpty() && moduleJson.get("name").getAsString().equalsIgnoreCase(module.getName())) {
                module.setKeyCode(moduleJson.get("key").getAsInt());
                if (!moduleJson.get("enabled").getAsBoolean())
                    module.toggleModule();
            }
        } catch (IOException e) {
            Shinrai.logger.error(e);
        }
    }

}
