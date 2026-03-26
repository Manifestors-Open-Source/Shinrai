/*
 *
 * Copyright © 2026 Manifestors Open Source
 * License: GPL-3.0
 * All code in this project is the property of the Manifestors Open Source team
 * and its contributors. If you use this code in any project, please provide proper attribution
 * and release your project under the GPL-3.0 license as well.
 * For more details, see: https://www.gnu.org/licenses/gpl-3.0.en.html
 *
 */

package com.manifestors.shinrai.client.file

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.manifestors.shinrai.client.Shinrai
import java.io.File
import java.lang.reflect.Type
import java.nio.file.Files
import java.nio.file.Path

object FileManager {
    private val ROOT_DIRECTORY = File("Shinrai")
    private val GSON = Gson()

    fun createDirectories() {
        if (ROOT_DIRECTORY.mkdirs()) Shinrai.logger.info("Shinrai directory initialized successfully.")
    }

    fun toJsonOnlyExposedFields(obj: Any?): String? {
        val gson = GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .setPrettyPrinting()
            .create()

        return gson.toJson(obj)
    }

    fun <T : Any> toJson(data: T?): String? {
        val gson = GsonBuilder().setPrettyPrinting().create()

        return gson.toJson(data)
    }

    fun writeJsonToFile(json: String, childDirectory: String, fileName: String) {
        runCatching {
            val folder = File(ROOT_DIRECTORY, childDirectory)
            val jsonFile = File(folder, fileName)
            folder.mkdirs()
            jsonFile.createNewFile()

            Files.writeString(Path.of(jsonFile.toURI()), json)
        }.onFailure { e ->
            Shinrai.logger.warn("Can't write json: ", e)
        }
    }

    fun writeJsonToFile(element: JsonElement?, childDirectory: String, fileName: String) {
        runCatching {
            val folder = File(ROOT_DIRECTORY, childDirectory)
            val jsonFile = File(folder, fileName)
            folder.mkdirs()
            jsonFile.createNewFile()

            Files.writeString(Path.of(jsonFile.toURI()), GSON.toJson(element))
        }.onFailure { e ->
            Shinrai.logger.warn("Can't write json: ", e)
        }
    }

    fun getJsonFromFile(childDirectory: String, fileName: String): String {
        runCatching {
            val folder = File(ROOT_DIRECTORY, childDirectory)
            val jsonFile = File(folder, fileName)

            return Files.readString(jsonFile.toPath())
        }.onFailure { e ->
            Shinrai.logger.warn("Can't read json: ", e)
            return ""
        }

        return ""
    }

    fun <T> getObjectFromJson(json: String?, type: Type): T? = GSON.fromJson<T?>(json, type)

}
