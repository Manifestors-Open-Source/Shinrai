package com.manifestors.shinrai.client.utils.file;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.manifestors.shinrai.client.Shinrai;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileManager {

    private static final File ROOT_DIRECTORY = new File("Shinrai");

    public static void createDirectories() {
        if (ROOT_DIRECTORY.mkdirs())
            Shinrai.logger.info("Shinrai directory initialized successfully.");
    }

    public static String toJsonOnlyExposedFields(Object obj) {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();

        return gson.toJson(obj);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void writeJsonToFile(String json, String childDirectory, String fileName) {
        try {
            var folder = new File(ROOT_DIRECTORY, childDirectory);
            var jsonFile = new File(folder, fileName);
            folder.mkdirs();
            jsonFile.createNewFile();

            Files.writeString(Path.of(jsonFile.toURI()), json);
        } catch (IOException e) {
            Shinrai.logger.error("Can't write json: ", e);
        }
    }

    public static String getJsonFromFile(String childDirectory, String fileName) {
        try {
            var folder = new File(ROOT_DIRECTORY, childDirectory);
            var jsonFile = new File(folder, fileName);

            return Files.readString(jsonFile.toPath());
        } catch (IOException e) {
            Shinrai.logger.error("Can't read json: ", e);
        }

        return "";
    }
}
